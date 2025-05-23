package utilities.selenium.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import utilities.common.PropertiesUtils;

public class FirefoxFactory implements DriverFactory<FirefoxOptions> {
    FirefoxOptions options;

    @Override
    public FirefoxOptions initializeOptions() {
        FirefoxProfile profile = new FirefoxProfile();
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        profile.setPreference("dom.webnotifications.enabled", false);
        options.setProfile(profile);
        options.addPreference("full-screen-api.enabled", true);
        options.addPreference("full-screen-api.allow-trusted-requests", true);
        options.addArguments("--kiosk");
        if ("true".equalsIgnoreCase(PropertiesUtils.getProperty("headless"))) {
            options.addArguments("--headless");
        }
        return options;
    }


    @Override
    public WebDriver createInstance() {
        options = initializeOptions();
        return new FirefoxDriver(options);
    }
}
