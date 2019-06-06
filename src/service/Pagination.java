package service;

public class Pagination {
	private int totalPages;
	private int beginPage;
	private int endPage;
	private int curPage; // 현재 페이지
	private int paginationCount; // 페이지 표시 갯수
	private int startRow;
	private int endRow;
	private int totalRows;
	
	public void processPaging (int totalRows, int pageNum, int rowsPerPage, int paginationPerPage) {
		//paginationPerPage 다른 페이지로 넘어갈 수 있는 갯수
		// pageNum은 지정된 값이 없는 경우 1
		// 정수 계산이므로 소수점은 날아간다
		this.totalRows = totalRows;
		curPage = pageNum;
		startRow = (pageNum - 1) * rowsPerPage + 1;
		endRow = startRow + rowsPerPage - 1;		    		
		paginationCount = paginationPerPage;
		
		//전체 페이지 갯수 처리 totalPages
		totalPages = totalRows / rowsPerPage; // 페이지당 행의 수로 총 행을 나누어 총 페이지를 구함(정수형)
		if((totalRows % rowsPerPage) > 0)
			totalPages++; // 남은 행이 있는 경우 1 페이지를 추가하여 총 페이지를 반환함
		
		if(totalPages > 0) { // 총 페이지가 1인 경우 시작 페이지도 1, 마지막 페이지도 1임
			beginPage = ((pageNum - 1) / paginationPerPage) * paginationPerPage + 1; // 현재 페이지를 시작 페이지로 정함
			endPage = beginPage + paginationPerPage - 1;
			if(endPage > totalPages)
				endPage = totalPages;
		}    //endPage  beginPage 한 화면에 보이는 페이지의 숫자의 처음과 끝
		
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getBeginPage() {
		return beginPage;
	}

	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getPaginationCount() {
		return paginationCount;
	}

	public void setPaginationCount(int paginationCount) {
		this.paginationCount = paginationCount;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}