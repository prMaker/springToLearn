/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.aop.framework;

import java.io.Serializable;

import org.springframework.util.Assert;

/**
 * Convenience superclass for configuration used in creating proxies,
 * to ensure that all proxy creators have consistent properties.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see AdvisedSupport
 */
/*convenience 方便的、便利的
* consistent 一致的
*
* */
public class ProxyConfig implements Serializable {

	/** use serialVersionUID from Spring 1.2 for interoperability */
	private static final long serialVersionUID = -8409359707199703185L;


	private boolean proxyTargetClass = false;

	/*optimize 最优化、使尽可能有效*/
	private boolean optimize = false;

	/*opaque 不透明的、晦暗的 设置是否可以将代理的对象设置为 {@link Advised} 接口对象 */
	boolean opaque = false;

	/*true 用于解决 单一对象中有两个AOP 方法、且其中一个AOP方法调用另一个AOP方法，但不能使用THIS调用*/
	boolean exposeProxy = false;

	/*冻结*/
	/**
	 * 见{@linnk frozen}
	 */
	private boolean frozen = false;


	/**
	 * Set whether to proxy the target class directly, instead of just proxying
	 * specific interfaces. Default is "false".
	 * <p>Set this to "true" to force proxying for the TargetSource's exposed
	 * target class. If that target class is an interface, a JDK proxy will be
	 * created for the given interface. If that target class is any other class,
	 * a CGLIB proxy will be created for the given class.
	 *
	 * FIXME yuanweijie 从新理解
	 * <p>Note: Depending on the configuration of the concrete proxy factory,
	 * the proxy-target-class behavior will also be applied if no interfaces
	 * have been specified (and no interface autodetection is activated).
	 * @see org.springframework.aop.TargetSource#getTargetClass()
	 */
	/*specific adj、具体的、明确的 n 细节、特性   concrete 具体的、有形的、实在的*/
	public void setProxyTargetClass(boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	/**
	 * Return whether to proxy the target class directly as well as any interfaces.
	 */
	public boolean isProxyTargetClass() {
		return this.proxyTargetClass;
	}

	/**
	 * Set whether proxies should perform aggressive optimizations.
	 * The exact meaning of "aggressive optimizations" will differ
	 * between proxies, but there is usually some tradeoff.
	 * Default is "false".
	 * <p>For example, optimization will usually mean that advice changes won't
	 * take effect after a proxy has been created. For this reason, optimization
	 * is disabled by default. An optimize value of "true" may be ignored
	 * if other settings preclude optimization: for example, if "exposeProxy"
	 * is set to "true" and that's not compatible with the optimization.
	 * 例如：优化将会总是意味着一个代理创建成功后，{ FIXME 通知的改变将不会生效}
	 * 优化设置在另一个优化之后设置便会被忽略
	 * 例如：{FIXME 如果暴露代理设置为对 那么和最优化是不兼容的}
	 */
	/*aggressive 积极的 optimize 优化  tradeoff 折中，权衡 compatible 兼容*/
	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	/**
	 * Return whether proxies should perform aggressive optimizations.
	 */
	public boolean isOptimize() {
		return this.optimize;
	}

	/**
	 * Set whether proxies created by this configuration should be prevented
	 * from being cast to {@link Advised} to query proxy status.
	 * <p>Default is "false", meaning that any AOP proxy can be cast to
	 * {@link Advised}.
	 * 设置 AOP 对象是否支持 可以转换为 {@link Advised} 接口 来查询AOP 代理状态
	 */
	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}

	/**
	 * Return whether proxies created by this configuration should be
	 * prevented from being cast to {@link Advised}.
	 */
	public boolean isOpaque() {
		return this.opaque;
	}

	/**
	 * Set whether the proxy should be exposed by the AOP framework as a
	 * ThreadLocal for retrieval via the AopContext class. This is useful
	 * 通过 {@link AopContext} 对象来回收
	 * if an advised object needs to call another advised method on itself.
	 * (If it uses {@code this}, the invocation will not be advised).
	 * 如果用了 this 调用将不会被 通知
	 * {TODO @see V V V V}
	 * <p>Default is "false", in order to avoid unnecessary extra interception.
	 * This means that no guarantees are provided that AopContext access will
	 * work consistently within any method of the advised object.
	 */
	public void setExposeProxy(boolean exposeProxy) {
		this.exposeProxy = exposeProxy;
	}

	/**
	 * Return whether the AOP proxy will expose the AOP proxy for
	 * each invocation.
	 */
	public boolean isExposeProxy() {
		return this.exposeProxy;
	}

	/**
	 * Set whether this config should be frozen.
	 * <p>When a config is frozen, no advice changes can be made. This is
	 * useful for optimization, and useful when we don't want callers to
	 * be able to manipulate configuration after casting to Advised.
	 * 设置这个配置应该会被冻结
	 * 当配置被冻结后，将不能再改变配置， 这对于最优化使有用的， 而且是有用的 当我们不想
	 * 调用者能够操作配置 当强制转换为一个通知后！
	 */
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	/**
	 * Return whether the config is frozen, and no advice changes can be made.
	 */
	public boolean isFrozen() {
		return this.frozen;
	}


	/**
	 * Copy configuration from the other config object.
	 * @param other object to copy configuration from
	 */
	public void copyFrom(ProxyConfig other) {
		Assert.notNull(other, "Other ProxyConfig object must not be null");
		this.proxyTargetClass = other.proxyTargetClass;
		this.optimize = other.optimize;
		this.exposeProxy = other.exposeProxy;
		this.frozen = other.frozen;
		this.opaque = other.opaque;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("proxyTargetClass=").append(this.proxyTargetClass).append("; ");
		sb.append("optimize=").append(this.optimize).append("; ");
		sb.append("opaque=").append(this.opaque).append("; ");
		sb.append("exposeProxy=").append(this.exposeProxy).append("; ");
		sb.append("frozen=").append(this.frozen);
		return sb.toString();
	}

}
