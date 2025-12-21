package org.tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

//Задача U8. Перезапуск упавших тестов
public class SingleTestRetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryCount = 2;

    @Override
    public boolean retry(ITestResult result) {
        if ("testFailingPageTitle".equals(result.getMethod().getMethodName())) {
            if (retryCount < maxRetryCount) {
                retryCount++;
                return true;
            }
        }
        return false;
    }
}
