package com.example.capstone.furniturestore;



import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


public class Example {




    @org.junit.Test
    public void setUp() throws MalformedURLException {


        DesiredCapabilities capabilities = DesiredCapabilities.android();
        capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, BrowserType.CHROME);
        capabilities.setCapability(MobileCapabilityType.PLATFORM, Platform.ANDROID);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Samsung S8");
        capabilities.setCapability(MobileCapabilityType.VERSION, "5.0.1");

        URL url = new URL("http://0.0.0.0:4723/wd/hub");

        WebDriver driver = new AndroidDriver(url, capabilities);
        driver.get("http://www.facebook.com");

        System.out.print("Title:"+ driver.getTitle());

        driver.quit();












    }


}
