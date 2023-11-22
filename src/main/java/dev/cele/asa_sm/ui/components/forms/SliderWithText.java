package dev.cele.asa_sm.ui.components.forms;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class SliderWithText extends JPanel {
    private final JSlider slider;
    private final NumberField numberField;

    @Getter
    private Number min = 0;
    @Getter
    private Number max = 100;
    @Getter
    private Number value = 0;
    @Getter
    private Number step = 1;

    public void setMin(Number min) {
        this.min = min;
        updateSlider();
    }

    public void setMax(Number max) {
        this.max = max;
        updateSlider();
    }

    public void setValue(Number value) {
        this.value = value;
        numberField.setNumber(value);
        updateSlider();
    }

    public void setStep(Number step) {
        this.step = step;
        if((double)step.intValue() != step.doubleValue()){
            numberField.setIsInteger(false);
        }
        updateSlider();
    }

    public void set(Number min, Number max, Number step){
        this.min = min;
        this.max = max;
        this.step = step;
        updateSlider();
    }

    public void set(Number min, Number max, Number step, Number value) {
        setValue(value);
        set(min, max, step);
    }


    private void updateSlider(){
        var stepMultiplier = 1 / step.doubleValue();

        slider.setMinimum((int)(stepMultiplier));
        slider.setMaximum((int)(max.doubleValue() * stepMultiplier));
        slider.setValue((int)(value.doubleValue() * stepMultiplier));

        if(stepMultiplier > 1){
            log.info("OR, min: {}, max: {}, step: {}", min, max, step);
            log.info("ED, min: {}, max: {}, step: {}", slider.getMinimum(), slider.getMaximum(), 1);
        }

        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
    }


    public SliderWithText(){
        this(0, 100, 0);
    }

    public SliderWithText(int min, int max, int value) {
        slider = new JSlider(min, max, value);

        this.min = min;
        this.max = max;
        this.value = value;
        this.step = 1;

        updateSlider();

        numberField = new NumberField(value);

        setLayout(new BorderLayout());
        add(slider, BorderLayout.CENTER);
        add(numberField, BorderLayout.EAST);

        slider.addChangeListener(e -> {
            if(!Objects.equals(this.value, slider.getValue())){
                var realValue = slider.getValue() * step.doubleValue();
                log.info("Slider value: {}, real value: {}", slider.getValue(), realValue);
                numberField.setNumber(realValue);
            }
        });

        numberField.addNumberListener(val -> {
            if(!Objects.equals(this.value, val)){
                this.value = val;
                slider.setValue((int)(val.doubleValue() * (1 / step.doubleValue())));
            }
        });

    }

    public void addChangeListener(Consumer<Number> listener) {
        numberField.addNumberListener(listener);
    }

}
