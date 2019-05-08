package top.andnux.customview.piechart;

public class PieChartBean {

    private int pieColor; //颜色
    private int percentage; //颜色
    private int lineColor;
    private String lineText;

    private int startAngle;
    private int endAngle;

    public PieChartBean() {
    }

    public PieChartBean(int pieColor, int percentage, int lineColor, String lineText) {
        this.pieColor = pieColor;
        this.percentage = percentage;
        this.lineColor = lineColor;
        this.lineText = lineText;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(int endAngle) {
        this.endAngle = endAngle;
    }

    public int getPieColor() {
        return pieColor;
    }

    public void setPieColor(int pieColor) {
        this.pieColor = pieColor;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public int getLineColor() {
        return lineColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public String getLineText() {
        return lineText;
    }

    public void setLineText(String lineText) {
        this.lineText = lineText;
    }
}
