pipeline {
    agent any

    tools {
        maven 'Maven-3.8.6'
        jdk 'JDK-17'
        allure 'Allure'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '20'))
        disableConcurrentBuilds()
        timeout(time: 30, unit: 'MINUTES')
    }

    parameters {
        choice(
            name: 'BRANCH',
            choices: ['main', 'brynza-probation'],
            description: 'Select branch to build'
        )
    }

    environment {
        ALLURE_RESULTS = 'target/allure-results'
        ALLURE_REPORT = 'allure-report'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${params.BRANCH}"]],
                    extensions: [],
                    userRemoteConfigs: [[
                        url: 'https://github.com/BrynzaA/AuthoTests.git',
                        credentialsId: ''
                    ]]
                ])
            }
        }

        stage('Copy Allure History') {
            steps {
                script {
                    sh """
                        mkdir -p ${ALLURE_RESULTS}
                    """

                    def lastSuccessful = currentBuild.rawBuild.parent.getLastSuccessfulBuild()
                    if (lastSuccessful != null) {
                        echo "Copying Allure history from build #${lastSuccessful.number}"

                        step([
                            $class: 'CopyArtifact',
                            projectName: env.JOB_NAME,
                            filter: "${ALLURE_REPORT}/history/**",
                            target: "${ALLURE_RESULTS}/",
                            selector: [
                                $class: 'SpecificBuildSelector',
                                buildNumber: lastSuccessful.number.toString()
                            ],
                            fingerprintArtifacts: true,
                            flatten: false
                        ])
                    } else {
                        echo "No previous successful build found. Starting fresh Allure history."
                    }
                }
            }
        }

        stage('Build & Test') {
            steps {
                sh """
                    mvn clean test \
                    -Dmaven.compiler.source=17 \
                    -Dmaven.compiler.target=17 \
                    -Djava.version=17
                """
            }

            post {
                always {
                    junit 'target/surefire-reports/*.xml'

                    archiveArtifacts artifacts: 'target/screenshots/**/*', allowEmptyArchive: true
                }
            }
        }

        stage('Generate Allure Report') {
            steps {
                sh """
                    # Генерируем отчет из результатов тестов
                    mvn allure:report

                    # Копируем сгенерированный отчет
                    if [ -d "target/site/allure-maven-plugin" ]; then
                        mkdir -p ${ALLURE_REPORT}
                        cp -r target/site/allure-maven-plugin/* ${ALLURE_REPORT}/ || true
                    fi
                """
            }
        }

        stage('Publish Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: "${ALLURE_RESULTS}"]],
                    report: "${ALLURE_REPORT}"
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: "${ALLURE_RESULTS}/**/*", allowEmptyArchive: true
            archiveArtifacts artifacts: "${ALLURE_REPORT}/**/*", allowEmptyArchive: true
            archiveArtifacts artifacts: 'target/surefire-reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: 'pom.xml', allowEmptyArchive: true
        }

        success {
            echo "Build #${env.BUILD_NUMBER} successful! Allure report: ${env.BUILD_URL}allure/"
        }

        failure {
            echo "Build #${env.BUILD_NUMBER} failed! Check console output: ${env.BUILD_URL}console"
        }

        unstable {
            echo "Build #${env.BUILD_NUMBER} unstable! Tests failing."
        }
    }
}