package io.demo.service.common;

@FunctionalInterface
public interface DoUpdateWriteRequestData<F, S, T> {
	void doUpdate(F f, S s, T t);
}
