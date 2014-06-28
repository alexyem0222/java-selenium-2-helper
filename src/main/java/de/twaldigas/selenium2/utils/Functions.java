package de.twaldigas.selenium2.utils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Helper Class especially for Selenium 2 / WebDriver.
 */
public class Functions {

	protected WebDriver driver;

	public Functions(final WebDriver driver) {
		this.driver = driver;
	}

	private static final int ELEMENT_WAIT_TIMEOUT_IN_SECONDS = 10;
	private static final int PAGE_WAIT_TIMEOUT_IN_SECONDS = 60;

	// Is Element Present

	/**
	 * @param element - {@link WebElement}.
	 * @return true if element present; else false.
	 */
	protected boolean isElementPresent(final WebElement element) {
		try {
			element.getTagName();
		} catch (final NoSuchElementException ignored) {
			return false;
		} catch (final StaleElementReferenceException ignored) {
			return false;
		}
		return true;
	}

	/**
	 * @param by - {@link WebElement} as {@link By} object.
	 * @return true if element present; else false.
	 */
	protected boolean isElementPresent(final By by) {
		return this.driver.findElements(by).size() > 0;
	}

	// Is Element Visible

	/**
	 * @param element - {@link WebElement}.
	 * @return true if element visible; else false.
	 */
	protected boolean isElementVisible(final WebElement element) {
		return element.isDisplayed();
	}

	/**
	 * @param by - {@link WebElement} as {@link By} object.
	 * @return true if element visible; else false.
	 */
	protected boolean isElementVisible(final By by) {
		return this.driver.findElement(by).isDisplayed();
	}

	// Is Any Text Present

	/**
	 * @param element - {@link WebElement}.
	 * @return true if any text present; else false.
	 */
	protected boolean isAnyTextPresent(final WebElement element) {
		final String text = element.getText();
		return StringUtils.isNotBlank(text);
	}

	/**
	 * @param by - {@link WebElement} as {@link By} object.
	 * @return true if any text present; else false.
	 */
	protected boolean isAnyTextPresent(final By by) {
		return isAnyTextPresent(this.driver.findElement(by));
	}

	// Wait For Element

	/**
	 * Wait for {@link WebElement} is present. 5 seconds, tops.
	 * 
	 * @param element - {@link WebElement}.
	 */
	protected void waitForElement(final WebElement element) {
		this.waitForElement(element, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
	}

	/**
	 * Wait for {@link WebElement} is present.
	 * 
	 * @param element - {@link WebElement}.
	 * @param timeoutInSeconds - How long the {@link WebDriver} wait for element.
	 */
	protected void waitForElement(final WebElement element, final int timeoutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Wait for {@link WebElement} is present. 5 seconds, tops.
	 * 
	 * @param by - {@link WebElement} as {@link By} object.
	 */
	protected void waitForElement(final By by) {
		waitForElement(by, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
	}

	/**
	 * Wait for {@link WebElement} is present.
	 * 
	 * @param by - {@link WebElement} as By object.
	 * @param timeoutInSeconds - How long the {@link WebDriver} wait for element.
	 */
	protected void waitForElement(final By by, final int timeoutInSeconds) {
		final WebDriverWait wait = new WebDriverWait(this.driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	// Wait For Element Is Invisible

	/**
	 * Wait for {@link WebElement} is invisible. 5 seconds, tops.
	 * 
	 * @param by - {@link WebElement} as {@link By} object.
	 */
	protected void waitForElementIsInvisible(final By by) {
		final WebDriverWait wait = new WebDriverWait(this.driver, ELEMENT_WAIT_TIMEOUT_IN_SECONDS);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
	}

	// Don't create a new method waitForElementIsInvisible for WebElement. It dosn't work.

	// Wait For Page Load

	/**
	 * Wait for page load. 60 seconds, tops.
	 */
	public void waitForPageLoad() {
		waitForPageLoad(PAGE_WAIT_TIMEOUT_IN_SECONDS);
	}

	/**
	 * Wait for page load.
	 * 
	 * @param timeoutInSeconds - How long the {@link WebDriver} wait for page load.
	 */
	public void waitForPageLoad(final int timeoutInSeconds) {
		waitForElement(By.tagName("html"), timeoutInSeconds);
	}

	// XPath Finder

	/**
	 * Search specified XPaths. Start with first value in array.
	 * 
	 * @param xpathList - XPaths as String.
	 * @return First XPath was founded. Was no XPath found null.
	 */
	protected String xpathFinder(final String... xpathList) {

		for (final String xpath : xpathList) {
			if (isElementPresent(By.xpath(xpath))) {
				return xpath;
			}
		}

		return null;

	}

	// Mouseover

	/**
	 * Hover over {@link WebElement}.
	 * 
	 * @param element - {@link WebElement}.
	 */
	protected void mouseover(final WebElement element) {
		final Actions builder = new Actions(this.driver);
		builder.moveToElement(element).build().perform();
	}

	/**
	 * Hover over {@link WebElement}.
	 * 
	 * @param by - {@link WebElement} as {@link By} object.
	 */
	protected void mouseover(final By by) {
		mouseover(this.driver.findElement(by));
	}

	// Drag And Drop

	/**
	 * Drag and drop from a specified {@link WebElement} to coordinates.
	 * 
	 * @param by - {@link WebElement}.
	 * @param xOffset - How many pixel move element left or right. A negative value is left.
	 * @param yOffset - How many pixel move element up or down. A negative value is up.
	 */
	protected void dragAndDrop(final WebElement element, final int xOffset, final int yOffset) {
		final Actions builder = new Actions(this.driver);
		final Action dragAndDrop = builder.clickAndHold(element).moveByOffset(xOffset, yOffset).release().build();
		dragAndDrop.perform();
	}

	/**
	 * Drag and drop from a specified {@link WebElement} to coordinates.
	 * 
	 * @param by - {@link WebElement} as {@link By} object.
	 * @param xOffset - How many pixel move element left or right. A negative value is left.
	 * @param yOffset - How many pixel move element up or down. A negative value is up.
	 */
	protected void dragAndDrop(final By by, final int xOffset, final int yOffset) {
		dragAndDrop(this.driver.findElement(by), xOffset, yOffset);
	}

	// Switch Window

	/**
	 * Switch to a open window.
	 * 
	 * @param url - A part of the URL from the window you want switch.
	 */
	public void switchWindow(final String url) {
		sleep(2000);
		String currentHandle = null;
		final Set<String> handles = this.driver.getWindowHandles();
		if (handles.size() > 1) {
			currentHandle = this.driver.getWindowHandle();
		}
		if (currentHandle != null) {
			for (final String handle : handles) {
				this.driver.switchTo().window(handle);
				if (this.driver.getCurrentUrl().contains(url) && currentHandle.equals(handle) == false) {
					break;
				}
			}
		} else {
			for (final String handle : handles) {
				this.driver.switchTo().window(handle);
				if (this.driver.getCurrentUrl().contains(url)) {
					break;
				}
			}
		}
	}

	// Is Readonly

	/**
	 * @param element - {@link WebElement}.
	 * @return If element read only true; else false.
	 */
	protected boolean isReadonly(final WebElement element) {
		return Boolean.valueOf(element.getAttribute("readonly")).booleanValue();
	}

	/**
	 * @param by - {@link WebElement} as {@link By}.
	 * @return If element read only true; else false.
	 */
	protected boolean isReadonly(final By by) {
		return isReadonly(this.driver.findElement(by));
	}

	// Get Element Position

	/**
	 * @param element - {@link WebElement}.
	 * @return Get position of element as {@link Point} object.
	 */
	protected Point getElementPosition(final WebElement element) {
		return element.getLocation();
	}

	/**
	 * @param element - {@link WebElement}.
	 * @return Get distance to left of element.
	 */
	protected int getElementPositionX(final WebElement element) {
		final Point pos = getElementPosition(element);
		return pos.getX();
	}

	/**
	 * @param element - {@link WebElement}.
	 * @return Get distance to top of element.
	 */
	protected int getElementPositionY(final WebElement element) {
		final Point pos = getElementPosition(element);
		return pos.getY();
	}

	// Backspace Input Clear

	/**
	 * Clear input with backspace key.
	 * 
	 * @param element - {@link WebElement}.
	 */
	protected void backspaceInputClear(final WebElement element) {
		final int numberOfCharacters = element.getAttribute("value").length();
		for (int i = 0; i <= numberOfCharacters; i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	/**
	 * Clear input with backspace key.
	 * 
	 * @param element - {@link WebElement}.
	 * @param numberOfCharacters - How many characters will delete.
	 */
	protected void backspaceInputClear(final WebElement element, final int numberOfCharacters) {
		for (int i = 0; i <= numberOfCharacters; i++) {
			element.sendKeys(Keys.BACK_SPACE);
		}
	}

	// Scroll

	/**
	 * Scroll in current window.
	 * 
	 * @param x - How many pixel scroll left or right. A negative value is left.
	 * @param y - How many pixel scroll up or down. A negative value is up.
	 */
	public void scroll(final int x, final int y) {
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		for (int i = 0; i <= x; i = i + 50) {
			js.executeScript("scroll(" + i + ",0)");
		}
		for (int j = 0; j <= y; j = j + 50) {
			js.executeScript("scroll(0," + j + ")");
		}
	}

	// Highlight WebElement

	/**
	 * Highlight a {@link WebElement}.
	 * 
	 * @param element - {@link WebElement}.
	 * @return {@link JavascriptExecuter} object.
	 */
	protected JavascriptExecutor highlightElementPermanently(final WebElement element) {
		final JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		return js;
	}

	/**
	 * Highlight a {@link WebElement} for 3 seconds.
	 * 
	 * @param element - {@link WebElement}.
	 */
	protected void highlightElement(final WebElement element) {
		final String originalStyle = element.getAttribute("style");
		final JavascriptExecutor js = highlightElementPermanently(element);
		sleep(3000);
		js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", element);
	}

	// Zoom

	/**
	 * Zoom current window +1.
	 */
	public void zoomPlus() {
		Actions actions = new Actions(this.driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.ADD).perform();
		actions = new Actions(this.driver);
		actions.keyUp(Keys.CONTROL).perform();
	}

	/**
	 * Zoom current window -1.
	 */
	public void zoomMinus() {
		Actions actions = new Actions(this.driver);
		actions.keyDown(Keys.CONTROL).sendKeys(Keys.SUBTRACT).perform();
		actions = new Actions(this.driver);
		actions.keyUp(Keys.CONTROL).perform();
	}

	// Take A Screenshot

	/**
	 * Take a screenshot.
	 * 
	 * @param path - Directory and name of screenshot.
	 */
	public void takeScreenshot(final String path) {
		final File scrFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(path));
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	// Alert

	/**
	 * @return true if alert present; else false.
	 */
	public boolean isAlertPresent() {
		try {
			final Alert alert = this.driver.switchTo().alert();
			alert.getText();
		} catch (final NoAlertPresentException nape) {
			return false;
		}
		return true;
	}

	/**
	 * @return Get text from alert.
	 */
	public String getAlertText() {
		final Alert alert = this.driver.switchTo().alert();
		return alert.getText();
	}

	/**
	 * Accept alert.
	 */
	public void acceptAlert() {
		final Alert alert = this.driver.switchTo().alert();
		alert.accept();
	}

	/**
	 * Cancel alert.
	 */
	public void dismissAlert() {
		final Alert alert = this.driver.switchTo().alert();
		alert.dismiss();
	}

	private void sleep(final int millis) {

		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
			// Nothing happens.
		}

	}

}
