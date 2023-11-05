package fr.cytech.superflash.Javaclass;

public class ChartData {
    private String label;
    private int[] values; 
    private String color;
   
    public ChartData(String label, int[] values, String color) {
        this.label = label;
        this.values = values;
        this.color = color;
    }

   
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int[] getValues() {
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}