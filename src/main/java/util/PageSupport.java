package util;

public class PageSupport {
    private int totalCount = 0;
    private int pageSize = Constants.PAGE_SIZE;
    private int totalPageCount = 1;

    public PageSupport(int totalCount){
        this.totalCount = totalCount;
        double temp = (double)pageSize;
        totalPageCount = (int) Math.ceil(totalCount/temp)>0?(int) Math.ceil(totalCount/temp):1;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        double temp = (double)pageSize;
        totalPageCount = (int) Math.ceil(totalCount/temp)>0?(int) Math.ceil(totalCount/temp):1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

}
