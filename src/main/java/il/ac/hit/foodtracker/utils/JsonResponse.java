package il.ac.hit.foodtracker.utils;


public  class JsonResponse <T extends Object>{
	private T data;

	public JsonResponse(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
