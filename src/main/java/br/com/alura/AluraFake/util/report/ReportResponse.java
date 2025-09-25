package br.com.alura.AluraFake.util.report;

import java.util.List;

public class ReportResponse<T> {

    private List<T> content;
    private Integer totalPublished;

    public ReportResponse(List<T> content, Integer totalPublished) {
        this.content = content;
        this.totalPublished = totalPublished;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Integer getTotalPublished() {
        return totalPublished;
    }

    public void setTotalPublished(Integer total) {
        this.totalPublished = total;
    }

}
