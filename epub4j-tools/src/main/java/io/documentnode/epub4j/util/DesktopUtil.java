
package io.documentnode.epub4j.util;

import java.awt.Desktop;
import java.net.URL;

public class DesktopUtil {
    
    /**
     * Open a URL in the default web browser.
     * 
     * @param url a URL to open in a web browser.
     * @return true if a browser has been launched.
     */
    public static boolean launchBrowser(URL url) throws BrowserLaunchException {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(url.toURI());
                return true;
            } catch (Exception ex) {
                throw new BrowserLaunchException("Browser could not be launched for "+url, ex);
            }
        }
        return false;
    }
    
    public static class BrowserLaunchException extends Exception {

        private BrowserLaunchException(String message, Throwable cause) {
            super(message, cause);
        }
        
    }
}
