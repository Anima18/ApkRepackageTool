/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ut.apkrepackage.util;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author jianjianhong
 */
public class FileSizeComparator implements Comparator<File> {
    public int compare( File a, File b ) {
        long aSize = a.length();
        long bSize = b.length();
        if ( aSize == bSize ) {
            return 0;
        }
        else {
            return Long.compare(aSize, bSize);
        }
    }
}
