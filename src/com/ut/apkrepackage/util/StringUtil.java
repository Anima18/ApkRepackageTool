/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.apkrepackage.util;

/**
 *
 * @author jianjianhong
 */
public class StringUtil {
    /**
	 * check Object obj is null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(final Object obj) {
		if (null != obj && !"".equals(obj)) {
			return false;
		}
		return true;
	}

	/**
	 * check Object obj is not null
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(final Object obj) {
		return !isEmpty(obj);
	}
}
