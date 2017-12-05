package org.apache.pi.rover.rover;

import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@SpringBootApplication
@CrossOrigin
public class RoverApplication {
    private static final Logger LOG = LoggerFactory.getLogger(RoverApplication.class);
    private GpioController gpio;

    private GpioPinDigitalOutput forward;
    private GpioPinDigitalOutput left;
    private GpioPinDigitalOutput right;
    private GpioPinDigitalOutput backward;

    public static void main(String[] args) {
        SpringApplication.run(RoverApplication.class, args);
    }

    @GetMapping(path = "drive")
    public String drive(@RequestParam String direction) throws InterruptedException {
        LOG.info("REQUEST  Direction = {}", direction);
        if ("rf".equalsIgnoreCase(direction)) {
            rightForward();
        } else if ("rb".equalsIgnoreCase(direction)) {
            rightBack();
        } else if ("lf".equalsIgnoreCase(direction)) {
            leftForward();
        } else if ("lb".equalsIgnoreCase(direction)) {
            leftBack();
        } else if ("f".equalsIgnoreCase(direction)) {
            forward();
        } else if ("b".equalsIgnoreCase(direction)) {
            backward();
        }
        LOG.info("RESPONSE Direction = {}", direction);
        return direction;
    }

    private void forward() throws InterruptedException {
        initialize();
        forward.low();
        Thread.sleep(100);
        forward.high();
    }

    private void backward() throws InterruptedException {
        initialize();
        backward.low();
        Thread.sleep(100);
        backward.high();
    }

    private void leftBack() throws InterruptedException {
        initialize();
        left.low();
        Thread.sleep(100);
        backward.low();
        Thread.sleep(300);
        backward.high();
        forward.low();
        Thread.sleep(20);
        forward.high();
        left.high();
    }

    private void leftForward() throws InterruptedException {
        initialize();
        left.low();
        Thread.sleep(100);
        forward.low();
        Thread.sleep(300);
        forward.high();
        backward.low();
        Thread.sleep(50);
        backward.high();
        left.high();
    }

    private void rightBack() throws InterruptedException {
        initialize();
        right.low();
        Thread.sleep(100);
        backward.low();
        Thread.sleep(300);
        backward.high();
        forward.low();
        Thread.sleep(20);
        forward.high();
        right.high();
    }

    private void rightForward() throws InterruptedException {
        initialize();
        right.low();
        Thread.sleep(100);
        forward.low();
        Thread.sleep(300);
        forward.high();
        backward.low();
        Thread.sleep(50);
        backward.high();
        right.high();
    }

    /**
     * NOTE : PIN Numbering are NOT SAME as hardware : http://pi4j.com/pins/model-zero-rev1.html
     */
    private void initialize() {
        if (gpio == null) {
            gpio = GpioFactory.getInstance();

            right = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "Right", PinState.HIGH);
            left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "Left", PinState.HIGH);
            forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "Forward", PinState.HIGH);
            backward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "Backward", PinState.HIGH);
        }

        right.high();
        left.high();
        forward.high();
        backward.high();
    }
}
