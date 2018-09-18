package app.filters;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;

@Component
public class PostFilter extends ZuulFilter {

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		System.out.println("Using Post Filter");
		return null;
	}

}