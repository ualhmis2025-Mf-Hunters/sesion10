package org.ual.hmis.grupo10;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import com.gargoylesoftware.htmlunit.BrowserVersion;



public class DefaultSuiteTest {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() throws Exception {
      int browser = 0; // 0: Firefox, 1: Chrome, 2: HtmlUnit
      Boolean headless = true;

      switch (browser) {
      case 0:  // Firefox
          System.setProperty("webdriver.gecko.driver", "drivers/geckodriver-v0.36.0-win64/geckodriver.exe");
          FirefoxOptions firefoxOptions = new FirefoxOptions();
          if (headless) firefoxOptions.addArguments("--headless");
          driver = new FirefoxDriver(firefoxOptions);
          break;

      case 1:  // Chrome
          System.setProperty("webdriver.chrome.driver", "drivers/chromedriver-win64/chromedriver.exe");
          ChromeOptions chromeOptions = new ChromeOptions();
          if (headless) chromeOptions.addArguments("--headless=new");
          chromeOptions.addArguments("window-size=1920,1080");
          driver = new ChromeDriver(chromeOptions);
          break;

//      case 2: // HtmlUnit
//          // Usa una versión de navegador "simulada" compatible con JS
//          driver = new HtmlUnitDriver(BrowserVersion.FIREFOX, true); // true = JavaScript habilitado
//          break;

      default:
          fail("Please select a browser");
      }

      js = (JavascriptExecutor) driver;
      vars = new HashMap<>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void creacioncorrecto() {
      driver.get("https://my-app-1748703446500.azurewebsites.net/login");
      driver.manage().window().setSize(new Dimension(1508, 695));
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      JavascriptExecutor js = (JavascriptExecutor) driver;

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7"))).sendKeys("admin", Keys.ENTER);
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8"))).sendKeys("admin", Keys.ENTER);
      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)"))).click();

      
      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-25"))).sendKeys("Jorge", Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-26"))).sendKeys("jorge", Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-27"))).sendKeys("jorge@gmail.com", Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-28"))).sendKeys("999 999 99", Keys.ENTER);

      wait.until(ExpectedConditions.elementToBeClickable(By.id("search-input-vaadin-date-picker-29"))).click();
      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".animate > vaadin-button:nth-child(1)"))).click();
      wait.until(ExpectedConditions.elementToBeClickable(By.id("search-input-vaadin-date-picker-29"))).sendKeys(Keys.ENTER);

      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-30"))).sendKeys("jorge", Keys.ENTER);
      wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-31"))).sendKeys("jorge", Keys.ENTER);

      wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(1)"))).click();

      WebElement sorter = wait.until(ExpectedConditions.visibilityOfElementLocated(
          By.cssSelector("vaadin-grid-cell-content:nth-child(13) > vaadin-grid-sorter")));
      js.executeScript("arguments[0].scrollIntoView({block: 'center'});", sorter);
      wait.until(ExpectedConditions.elementToBeClickable(sorter)).click();
      wait.until(ExpectedConditions.elementToBeClickable(sorter)).click();

      try {
          Thread.sleep(2000); 
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }

      List<WebElement> celdas = driver.findElements(By.cssSelector("vaadin-grid-cell-content"));
      boolean encontrado = false;
      for (WebElement celda : celdas) {
          String texto = celda.getText().trim();
          if ("Jorge".equalsIgnoreCase(texto)) {
              encontrado = true;
              break;
          }
      }

      assertThat("El texto 'Jorge' no se encontró en el grid", encontrado, is(true));
  }





  @Test
  public void eliminacioncorrecto() {
    driver.get("https://my-app-1748703446500.azurewebsites.net/login");
    driver.manage().window().setSize(new Dimension(1508, 695));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
    username.sendKeys("admin", Keys.ENTER);

    WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
    password.sendKeys("admin", Keys.ENTER);

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));

    WebElement accederBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", accederBtn);
    accederBtn.click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("vaadin-grid")));

    List<WebElement> celdas = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("vaadin-grid-cell-content")));
    WebElement primeraCelda = celdas.get(0);

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", primeraCelda);
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", primeraCelda);

    WebElement eliminarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(3)")));
    eliminarBtn.click();

    WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("vaadin-text-field:nth-child(1)")));
    assertThat(field.getText(), is("First Name"));
  }

  @Test
  public void eliminacionincorrecto() {
      driver.get("https://my-app-1748703446500.azurewebsites.net/login");
      driver.manage().window().setSize(new Dimension(1508, 695));
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      JavascriptExecutor js = (JavascriptExecutor) driver;

      
      WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
      username.sendKeys("admin", Keys.ENTER);

      WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
      password.sendKeys("admin", Keys.ENTER);

      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));

      WebElement accederBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
      js.executeScript("arguments[0].scrollIntoView(true);", accederBtn);
      accederBtn.click();

      WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-25")));
      searchInput.click();
      searchInput.clear();
      searchInput.sendKeys("antonio", Keys.ENTER);

      try {
          Thread.sleep(1500); 
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }

      WebElement eliminarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(3)")));
      eliminarBtn.click();

      try {
          Thread.sleep(1500);
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }

      searchInput.click();
      searchInput.clear();
      searchInput.sendKeys("antonio", Keys.ENTER);

      try {
          Thread.sleep(1500);
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }

      List<WebElement> celdas = driver.findElements(By.cssSelector("vaadin-grid-cell-content"));
      boolean encontrado = false;
      for (int i = 0; i < celdas.size(); i++) {
          String texto = celdas.get(i).getText().trim();
          if ("antonio".equalsIgnoreCase(texto)) {
              encontrado = true;
              break;
          }
      }

      assertThat("El usuario 'antonio' no debería existir tras eliminarlo", encontrado, is(false));
  }



  @Test
  public void logincorrecto() {
	  driver.get("https://my-app-1748703446500.azurewebsites.net/login");
	  driver.manage().window().setSize(new Dimension(1508, 695));
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	  WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
	  username.sendKeys("user");

	  WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
	  password.sendKeys("user");

	  WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);

	  loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
	  loginBtn.click();

	  WebElement editorForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".editor")));
	  assertThat(editorForm.getText(), is("First Name\nLast Name\nEmail\nPhone\nDate Of Birth\nOccupation\nRole\nImportant"));
	}
  @Test
  public void loginincorrecto() {
	  driver.get("https://my-app-1748703446500.azurewebsites.net/login");
	  driver.manage().window().setSize(new Dimension(1508, 695));
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	  WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
	  username.sendKeys("user");

	  WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
	  password.sendKeys("usuario");

	  WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
	  loginBtn.click();

	  WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));
	  String mensaje = loginForm.getText();

	  assertThat("Mensaje de error no encontrado", mensaje.contains("Incorrect username or password"), is(true));

	}
  @Test
  public void modificacioncorrecto() {
	  driver.get("https://my-app-1748703446500.azurewebsites.net/login");
	  driver.manage().window().setSize(new Dimension(1508, 695));
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	  WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
	  username.sendKeys("admin");

	  WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
	  password.sendKeys("admin");

	  WebElement accederBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", accederBtn);
	  accederBtn.click();

	  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("vaadin-login-form-wrapper")));

	  WebElement gridCell = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-grid-cell-content")));
	  gridCell.click();

	  WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-26")));
	  inputField.click();
	  inputField.clear();
	  inputField.sendKeys("Aviles", Keys.ENTER);

	  WebElement guardarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(1)")));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", guardarBtn);
	  guardarBtn.click();
	}


  @Test
  public void modificacionincorrecto() {
      driver.get("https://my-app-1748703446500.azurewebsites.net/login");
      driver.manage().window().setSize(new Dimension(1508, 695));
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      JavascriptExecutor js = (JavascriptExecutor) driver;

      WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7")));
      username.sendKeys("admin", Keys.ENTER);

      WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8")));
      password.sendKeys("admin", Keys.ENTER);

      wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));

      WebElement accederBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)")));
      js.executeScript("arguments[0].scrollIntoView(true);", accederBtn);
      accederBtn.click();

      WebElement gridCell = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-grid-cell-content:nth-child(35)")));
      gridCell.click();

      WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-27")));
      inputField.click();
      inputField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE); 
      inputField.sendKeys("jean.rhodes"); 

     
      WebElement guardarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(1)")));
      js.executeScript("arguments[0].scrollIntoView(true);", guardarBtn);
      guardarBtn.click();

      WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
          By.xpath("//*[contains(text(),'must be a well-formed email address')]")
      ));

      assertThat(errorMsg.getText(), is("must be a well-formed email address"));
  }





  @Test
  public void registrousuarioscorrecto() {
	    driver.get("https://my-app-1748703446500.azurewebsites.net/register");
	    driver.manage().window().setSize(new Dimension(1508, 695));

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    String userRandom = "usuario" + (int)(Math.random() * 1000000);

	    WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-13")));
	    usernameField.click();
	    usernameField.sendKeys(userRandom, Keys.ENTER);

	    WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-14")));
	    nameField.click();
	    nameField.sendKeys(userRandom, Keys.ENTER);

	    WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-password-field-15")));
	    passwordField.click();
	    passwordField.sendKeys(userRandom, Keys.ENTER);

	    WebElement confirmPasswordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-password-field-16")));
	    confirmPasswordField.click();
	    confirmPasswordField.sendKeys(userRandom, Keys.ENTER);

	    WebElement registerBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button")));
	    registerBtn.click();

	    WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));
	    assertThat(loginForm.getText(), is("Log in\nUsername\nPassword\nLog in"));
	  }

  @Test
  public void registrousuariosincorrecto() {
	  driver.get("https://my-app-1748703446500.azurewebsites.net/register");
	  driver.manage().window().setSize(new Dimension(1508, 695));
	  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	  WebElement nombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-13")));
	  nombre.sendKeys("user", Keys.ENTER);

	  WebElement apellido = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-14")));
	  apellido.sendKeys("user", Keys.ENTER);

	  WebElement password1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-15")));
	  password1.sendKeys("user", Keys.ENTER);

	  WebElement password2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-16")));
	  password2.sendKeys("user", Keys.ENTER);

	  WebElement registrarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button")));
	  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registrarBtn);
	  registrarBtn.click();

	  WebElement titulo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3")));
	  assertThat(titulo.getText(), is("Register"));
	}
 
 
  @Test
  public void creacionincorrecto() {
    driver.get("https://my-app-1748703446500.azurewebsites.net/login");
    driver.manage().window().setSize(new Dimension(1508, 695));
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-text-field-7"))).sendKeys("admin", Keys.ENTER);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-vaadin-password-field-8"))).sendKeys("admin", Keys.ENTER);

    wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(2)"))).click();

    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-25"))).sendKeys("ale", Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-26"))).sendKeys("ale", Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-27"))).sendKeys("ale", Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-28"))).sendKeys("ale", Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-30"))).sendKeys("ale", Keys.ENTER);
    wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-31"))).sendKeys("ale", Keys.ENTER);

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button:nth-child(1)"))).click();

    String mensaje = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error-message-vaadin-text-field-9"))).getText();
    assertThat(mensaje, is("must be a well-formed email address"));
  }


  private void rellenarCampo(WebDriver driver, WebDriverWait wait, String fieldId, String texto) {
    WebElement campo = wait.until(ExpectedConditions.elementToBeClickable(By.id(fieldId)));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", campo);
    campo.click();
    campo.clear();
    campo.sendKeys(texto, Keys.ENTER);
  }

  @Test
  public void comprobarlenguaje() {
    // Test name: comprobar-lenguaje
    // Step # | name | target | value
    // 1 | open | https://my-app-1748703446500.azurewebsites.net/login | 
    driver.get("https://my-app-1748703446500.azurewebsites.net/login");
    // 2 | setWindowSize | 1509x694 | 
    driver.manage().window().setSize(new Dimension(1509, 694));
    // 3 | executeScript | return navigator.language | browserLanguage
    vars.put("browserLanguage", js.executeScript("return navigator.language"));
    // 4 | if | ${browserLanguage}=="en-US" | 
    if ((Boolean) js.executeScript("return (arguments[0]==\'en-US\')", vars.get("browserLanguage"))) {
      // 5 | echo | El idioma del navegador es inglés | 
      System.out.println("El idioma del navegador es inglés");
      // 6 | elseIf | ${browserLanguage}=="es-ES" | 
    } else if ((Boolean) js.executeScript("return (arguments[0]==\'es-ES\')", vars.get("browserLanguage"))) {
      // 7 | echo | El idioma del navegador es español | 
      System.out.println("El idioma del navegador es español");
      // 8 | else |  | 
    } else {
      // 9 | echo | El idioma no es soportado: ${browserLanguage} | 
    	System.out.println("El idioma no es soportado: " + vars.get("browserLanguage").toString());
      // 10 | end |  | 
    }
  }
  @Test
  public void comprobarnavegador() {
    // Test name: comprobar-navegador
    // Step # | name | target | value
    // 1 | open | https://my-app-1748703446500.azurewebsites.net/login | 
    driver.get("https://my-app-1748703446500.azurewebsites.net/login");
    // 2 | setWindowSize | 1509x694 | 
    driver.manage().window().setSize(new Dimension(1509, 694));
    // 3 | executeScript | return navigator.userAgent | userAgent
    vars.put("userAgent", js.executeScript("return navigator.userAgent"));
    // 4 | if | ${userAgent}.includes("Chrome") | 
    if ((Boolean) js.executeScript("return (arguments[0].includes(\'Chrome\'))", vars.get("userAgent"))) {
      // 5 | echo | Navegador detectado: Chrome | 
      System.out.println("Navegador detectado: Chrome");
      // 6 | elseIf | ${userAgent}.includes("Edg") | 
    } else if ((Boolean) js.executeScript("return (arguments[0].includes(\'Edg\'))", vars.get("userAgent"))) {
      // 7 | echo | Navegador detectado: Microsoft Edge | 
      System.out.println("Navegador detectado: Microsoft Edge");
      // 8 | elseIf | ${userAgent}.includes("Firefox") | 
    } else if ((Boolean) js.executeScript("return (arguments[0].includes(\'Firefox\'))", vars.get("userAgent"))) {
      // 9 | echo | Navegador detectado: Firefox | 
      System.out.println("Navegador detectado: Firefox");
      // 10 | else |  | 
    } else {
      // 11 | echo | Navegador no detectado: ${userAgent} | 
      System.out.println("Navegador no detectado: " + vars.get("userAgent").toString());
      // 12 | end |  | 
    }
  }
  @Test
  public void registrousuarioscorrectoautomatizado() {
      driver.get("https://my-app-1748703446500.azurewebsites.net/register");
      driver.manage().window().setSize(new Dimension(1508, 695));

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
      js = (JavascriptExecutor) driver;

      String userrandom = "usuario" + (int)(Math.random() * 1500000);
      System.out.println("Registrando nuevo usuario: " + userrandom);

      WebElement username = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-13")));
      username.click();
      username.sendKeys(userrandom, Keys.ENTER);

      WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-text-field-14")));
      name.click();
      name.sendKeys(userrandom, Keys.ENTER);

      WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-password-field-15")));
      password.click();
      password.sendKeys(userrandom, Keys.ENTER);

      WebElement confirmPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("input-vaadin-password-field-16")));
      confirmPassword.click();
      confirmPassword.sendKeys(userrandom, Keys.ENTER);

      WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("vaadin-button")));
      registerButton.click();

      WebElement loginForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("vaadinLoginFormWrapper")));
      String loginText = loginForm.getText().replace("\n", "").trim();

      assertThat(loginText, is("Log inUsernamePasswordLog in"));
  }


}
