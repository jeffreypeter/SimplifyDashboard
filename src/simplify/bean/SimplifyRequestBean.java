package simplify.bean;

public class SimplifyRequestBean {
	
	private SimplifyRequestHeadBean head = new SimplifyRequestHeadBean();
	private SimplifyBodyBean body = new SimplifyBodyBean();
	public SimplifyRequestHeadBean getHead() {
		return head;
	}
	public void setHead(SimplifyRequestHeadBean head) {
		this.head = head;
	}
	public SimplifyBodyBean getBody() {
		return body;
	}
	public void setBody(SimplifyBodyBean body) {
		this.body = body;
	}
	
}

