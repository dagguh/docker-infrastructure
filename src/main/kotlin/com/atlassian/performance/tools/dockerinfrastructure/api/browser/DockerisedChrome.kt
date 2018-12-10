package com.atlassian.performance.tools.dockerinfrastructure.api.browser

import com.atlassian.performance.tools.dockerinfrastructure.Ryuk
import com.atlassian.performance.tools.dockerinfrastructure.network.SharedNetwork
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.testcontainers.containers.BrowserWebDriverContainer
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy

class DockerisedChrome : Browser {

    private val browser: BrowserWebDriverContainerImpl = BrowserWebDriverContainerImpl()
        .withDesiredCapabilities(DesiredCapabilities.chrome())
        .withRecordingMode(BrowserWebDriverContainer.VncRecordingMode.SKIP, null)
        .waitingFor(HostPortWaitStrategy())
        .withNetwork(SharedNetwork(SharedNetwork.DEFAULT_NETWORK_NAME))
        .withExposedPorts(4444)

    override fun start(): RemoteWebDriver {
        Ryuk.disable()
        browser.start()
        return browser.webDriver
    }

    override fun close() {
        browser.webDriver.quit()
        browser.close()
    }
}

private class BrowserWebDriverContainerImpl() : BrowserWebDriverContainer<BrowserWebDriverContainerImpl>()
