package entity;

import java.util.List;

public class PageResult<T> {
    private long total;
    private List<T> row;

    public PageResult() {
    }

    public PageResult(long total, List<T> row) {
        this.total = total;
        this.row = row;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRow() {
        return row;
    }

    public void setRow(List<T> row) {
        this.row = row;
    }
}
