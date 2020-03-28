package emskinesispoc.bean;


import lombok.Data;

@Data
public class Order {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
