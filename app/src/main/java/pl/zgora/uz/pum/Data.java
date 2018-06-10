package pl.zgora.uz.pum;

public class Data {

    private String temperature;
    private String humidity;

    public Data() {
    }

    public Data(String temperature, String humidity) {

        this.temperature = temperature;
        this.humidity = humidity;
    }


    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }


    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
