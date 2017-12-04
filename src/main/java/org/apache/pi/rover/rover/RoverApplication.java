package org.apache.pi.rover.rover;

import com.pi4j.io.gpio.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
@SpringBootApplication
public class RoverApplication {
    private GpioController gpio;

    private GpioPinDigitalOutput forward;
    private GpioPinDigitalOutput left;
    private GpioPinDigitalOutput right;
    private GpioPinDigitalOutput backward;

    public static void main(String[] args) {
        SpringApplication.run(RoverApplication.class, args);
    }

    @GetMapping(path = "drive")
    public String drive(@RequestParam String direction) {
        return direction;
    }

    /**
     * NOTE : PIN Numbering are NOT SAME as hardware : http://pi4j.com/pins/model-zero-rev1.html
     */
    private void initialize() {
        if (gpio == null) {
            gpio = GpioFactory.getInstance();

            right = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "Right", PinState.LOW);
            left = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "Left", PinState.LOW);
            forward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "Forward", PinState.LOW);
            backward = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22, "Backward", PinState.LOW);
        }

        right.low();
        left.low();
        forward.low();
        backward.low();
    }

    @RequestMapping("/")
    public void all() {
        initialize();
        forward.high();
        left.high();
        right.high();
    }

    @RequestMapping("/green")
    public void green() {
        initialize();
        right.high();
    }

    @RequestMapping("/yellow")
    public void yellow() {
        initialize();
        left.high();
    }

    @RequestMapping("/red")
    public void red() {
        initialize();
        forward.high();
    }
}
