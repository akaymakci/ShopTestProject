package Utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.output.NullOutputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GWD {

    private static WebDriver driver;
    //Bana neler lazım:  1 browser tipi lazım burada ona göre oluşturucam
    // her bir paralel çalışan süreç için sadece o sürece özel static bir değişken lazım
    // çünkü runner classdaki işaret edilen tüm senaryolarda aynısını çalışması lazım.
    // demekki her pipeline için (Local) ve de ona özel static bir drivera ihtiyacım var
    //donanımdaki adı pipeline , yazılımdaki adı Thread , paralel çalışan her bir süreç

    private static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>(); // WebDriver 1 WebbDriver 2
    public static ThreadLocal<String> threadBrowserName = new ThreadLocal<>(); // chrome , firefox ...

    // threadDriver.get() -> ilgili tread deki driveri verecek
    // threadDriver.set(driver) -> ilgili thread e driver set ediliyor.

    public static WebDriverWait wait;
    public static WebDriver getDriver() {

        Logger.getLogger("").setLevel(Level.SEVERE);
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "Error");

        if(threadBrowserName.get()==null) { // parallel calismayan yani XMLden parametreyle gelmeyenler calissin diye
            threadBrowserName.set("chrome");
        }



        if (threadDriver.get() == null) { //Sistemde sadece bir defa driver calissin diye

            // extend report türkçe bilg çalışmaması sebebiyle kondu
            Locale.setDefault(new Locale("EN"));
            System.setProperty("user.language", "EN");

            String browserName = threadBrowserName.get(); // bu threaddeki browsernamei verdi.

            switch (browserName)
            {

                case"chrome":
                    System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
                    DriverService.Builder<ChromeDriverService, ChromeDriverService.Builder> serviceBuilder = new ChromeDriverService.Builder();
                    ChromeDriverService chromeDriverService = serviceBuilder.build();
                    chromeDriverService.sendOutputTo(NullOutputStream.NULL_OUTPUT_STREAM);

                    if (!runningFromIntelliJ()) {
                        ChromeOptions opt = new ChromeOptions();
                        opt.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1400,2400");
                        threadDriver.set(new ChromeDriver(opt));
                    }
                    else {

                        threadDriver.set(new ChromeDriver());
                    }

                    break;
                case"firefox":
                    System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"null");
                    WebDriverManager.firefoxdriver().setup();
                    threadDriver.set(new FirefoxDriver());  // bu thread e firefox istenmişşse ve yoksa bir tane ekleniyor
                    break;
                case"safari":
                    WebDriverManager.safaridriver().setup();
                    threadDriver.set(new SafariDriver());  // bu thread e firefox istenmişşse ve yoksa bir tane ekleniyor
                    break;
                case"edge":
                    System.setProperty(EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY,"true");
                    DriverService.Builder<EdgeDriverService, EdgeDriverService.Builder> serviceBuilderEdge = new EdgeDriverService.Builder();
                    EdgeDriverService edgeDriverService = serviceBuilderEdge.build();
                    edgeDriverService.sendOutputTo(NullOutputStream.NULL_OUTPUT_STREAM);
                    WebDriverManager.edgedriver().setup();
                    threadDriver.set(new EdgeDriver());  // bu thread e firefox istenmişşse ve yoksa bir tane ekleniyor
                    break;
            }

        }

        return threadDriver.get();
    }

    public static void quitDriver(){

        Bekle(3);

        if(threadDriver.get() != null){ //driver varsa
            threadDriver.get().quit();

            WebDriver driver = threadDriver.get();
            driver=null;
            threadDriver.set(driver);
        }
    }

    public static void Bekle(int saniye) {
        try {
            Thread.sleep(saniye * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean runningFromIntelliJ(){
        String classPath = System.getProperty("java.class.path");
        return classPath.contains("idea_rt.jar");
    }
}
