pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'CI4',
                    url: 'https://github.com/BrynzaA/AuthoTests.git'
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    # Запускаем тесты через docker-compose
                    docker-compose up --build --abort-on-container-exit
                '''
            }
        }

        stage('Save Report') {
            steps {
                sh '''
                    # Копируем отчет Allure
                    mkdir -p allure-report
                    cp -r target/site/allure-maven-plugin/* allure-report/ 2>/dev/null || true
                '''

                allure([
                    results: [[path: 'target/allure-results']],
                    reportBuildPolicy: 'ALWAYS'
                ])
            }
        }
    }

    post {
        always {
            archiveArtifacts artifacts: 'allure-report/**/*, target/screenshots/**/*'

            sh 'docker-compose down -v'
        }
    }
}