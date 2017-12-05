package org.apache.pi.rover.rover;

import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    private GpioController gpio;

    private GpioPinDigitalOutput forward;
    private GpioPinDigitalOutput left;
    private GpioPinDigitalOutput right;
    private GpioPinDigitalOutput backward;

    public GreetingController() {
        initialize();
    }

    private void initialize() {
        if (gpio == null) {
            gpio = GpioFactory.getInstance();

            right = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "Right", PinState.HIGH);
            right.setMode(PinMode.DIGITAL_OUTPUT);
            left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "Left", PinState.HIGH);
            left.setMode(PinMode.DIGITAL_OUTPUT);
            forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "Forward", PinState.HIGH);
            forward.setMode(PinMode.DIGITAL_OUTPUT);
            backward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "Backward", PinState.HIGH);
            backward.setMode(PinMode.DIGITAL_OUTPUT);

            LOG.info("INITIALIZED IN IF");
        }

        right.high();
        left.high();
        forward.high();
        backward.high();

        LOG.info("V3 right {} left {} forward {} backward {}", right.isHigh(), left.isHigh(), forward.isHigh(), backward.isHigh());
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @MessageMapping("/move")
    @SendTo("/topic/drive")
    public Greeting drive(HelloMessage message) throws Exception {
        switch (message.getName().toLowerCase()) {
            case "f": forward();LOG.info("Forward");break;
            case "b": backward();LOG.info("Back");break;
            case "rb": rightBack();LOG.info("RightBack");break;
            case "lb": leftBack();LOG.info("LeftBack");break;
            case "rf": rightForward();LOG.info("RightForward");break;
            case "lf": leftForward();LOG.info("LeftForward");break;
            default: LOG.info("No Command");break;

        }
        return new Greeting("Received message: " + message.toString());
    }

    private void forward() throws InterruptedException {
//        initialize();
        forward.low();
        Thread.sleep(100);
        forward.high();
    }

    private void backward() throws InterruptedException {
//        initialize();
        backward.low();
        Thread.sleep(100);
        backward.high();
    }

    private void leftBack() throws InterruptedException {
//        initialize();
        left.low();
        Thread.sleep(100);
        backward.low();
        Thread.sleep(500);
        backward.high();
        forward.low();
        Thread.sleep(20);
        forward.high();
        left.high();
    }

    private void leftForward() throws InterruptedException {
//        initialize();
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
//        initialize();
        right.low();
        Thread.sleep(100);
        backward.low();
        Thread.sleep(500);
        backward.high();
        forward.low();
        Thread.sleep(20);
        forward.high();
        right.high();
    }

    private void rightForward() throws InterruptedException {
//        initialize();
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

}