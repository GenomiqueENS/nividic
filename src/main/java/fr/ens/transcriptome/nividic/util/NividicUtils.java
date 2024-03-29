/*
 *                      Nividic development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the microarray platform
 * of the �cole Normale Sup�rieure and the individual authors.
 * These should be listed in @author doc comments.
 *
 * For more information on the Nividic project and its aims,
 * or to join the Nividic mailing list, visit the home page
 * at:
 *
 *      http://www.transcriptome.ens.fr/nividic
 *
 */

package fr.ens.transcriptome.nividic.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utilitity class.
 * @author Laurent jourdren
 */
public final class NividicUtils {

  private static int BUFFER_SIZE = 32 * 1024;

  /**
   * Do nothing.
   */
  public static void nop() {
  }

  /**
   * yeild the process.
   */
  public static void yield() {

    while (true) {
      Thread.yield();
    }
  }

  /**
   * Test if two array of string are equals.
   * @param a First array of string to test
   * @param b Second array of string to test
   * @return true if the two array of string are equals, false if not
   */
  public static boolean stringsEquals(final String[] a, final String[] b) {

    if (a == null && b == null)
      return true;
    if (a == null || b == null)
      return false;

    if (a.length != b.length)
      return false;

    for (int i = 0; i < a.length; i++) {

      if (a[i] == null && b[i] != null)
        return false;
      if (!a[i].equals(b[i]))
        return false;
    }

    return true;
  }

  /**
   * Test if two array of integers are equals.
   * @param a First array of integers to test
   * @param b Second array of integer to test
   * @return true if the two array of integers are equals, false if not
   */
  public static boolean intsEquals(final int[] a, final int[] b) {

    if (a == null && b == null)
      return true;
    if (a == null || b == null)
      return false;

    if (a.length != b.length)
      return false;

    for (int i = 0; i < a.length; i++)
      if (a[i] != b[i])
        return false;

    return true;
  }

  /**
   * converts an array of double in an array of int.
   * @param array Array of double to convert
   * @return an array of int
   */
  public static int[] arrayDoubleToArrayInt(final double[] array) {

    if (array == null)
      return null;

    final int[] result = new int[array.length];
    final int n = array.length;

    for (int i = 0; i < n; i++) {
      result[i] = (int) array[i];
    }

    return result;
  }

  /**
   * converts an array of int in an array of double.
   * @param array Array of int to convert
   * @return an array of double
   */
  public static double[] arrayIntToArrayDouble(final int[] array) {

    if (array == null)
      return null;

    final double[] result = new double[array.length];
    final int n = array.length;

    for (int i = 0; i < n; i++) {
      result[i] = array[i];
    }

    return result;
  }

  /**
   * Test if two stream are equals
   * @param a First stream to compare
   * @param b Second stream to compare
   * @return true if the two stream are equals
   * @throws IOException if an error occurs while reading the streams
   */
  public static boolean compareFile(final InputStream a, final InputStream b)
      throws IOException {

    if (a == null && b == null)
      return true;
    if (a == null || b == null)
      return false;

    boolean end = false;
    boolean result = true;

    while (!end) {

      int ca = a.read();
      int cb = b.read();

      if (ca != cb) {
        result = false;
        end = true;
      }

      if (ca == -1)
        end = true;

    }

    a.close();
    b.close();

    return result;
  }

  /**
   * Test if two stream are equals
   * @param filenameA First filename to compare
   * @param filenameB Second filename to compare
   * @return true if the two stream are equals
   * @throws IOException if an error occurs while reading the streams
   */
  public static boolean compareFile(final String filenameA,
      final String filenameB) throws IOException {
    return compareFile(new FileInputStream(filenameA), new FileInputStream(
        filenameB));
  }

  /**
   * Test if two stream are equals
   * @param fileA First filename to compare
   * @param fileB Second filename to compare
   * @return true if the two stream are equals
   * @throws IOException if an error occurs while reading the streams
   */
  public static boolean compareFile(final File fileA, final File fileB)
      throws IOException {
    return compareFile(new FileInputStream(fileA), new FileInputStream(fileB));
  }

  /**
   * Convert a collection of strings to an array of Strings.
   * @param col collection to convert
   * @return a new array of String
   */
  public static String[] toArray(final Collection<String> col) {

    if (col == null)
      return null;

    final String[] result = new String[col.size()];

    int i = 0;
    for (String s : col)
      result[i++] = s;

    return result;
  }

  /**
   * Convert a collection of int to an array of ints.
   * @param col collection to convert
   * @return a new array of ints
   */
  public static int[] toIntArray(final Collection<Integer> col) {

    if (col == null)
      return null;

    final int[] result = new int[col.size()];

    int i = 0;
    for (Integer val : col)
      result[i++] = val;

    return result;
  }

  /**
   * Convert a collection of doubles to an array of doubles.
   * @param col collection to convert
   * @return a new array of double
   */
  public static double[] toDoubleArray(final Collection<Double> col) {

    if (col == null)
      return null;

    final double[] result = new double[col.size()];

    int i = 0;
    for (Double val : col)
      result[i++] = val;

    return result;
  }

  /**
   * Convert an array of int to an array of double
   * @param array Array to convert
   * @return a new array of double
   */
  public static double[] toArrayDouble(final int[] array) {

    if (array == null)
      return null;

    final double[] result = new double[array.length];

    for (int i = 0; i < result.length; i++)
      result[i] = array[i];

    return result;
  }

  /**
   * Convert an array of int to an array of double
   * @param array Array to convert
   * @return a new array of double
   */
  public static double[] toArrayDouble(final String[] array) {

    if (array == null)
      return null;

    final double[] result = new double[array.length];

    for (int i = 0; i < result.length; i++)
      if (array[i] != null)
        result[i] = Double.parseDouble(array[i]);

    return result;
  }

  /**
   * Convert an array of double to an array of int
   * @param array Array to convert
   * @return a new array of double
   */
  public static int[] toArrayInt(final double[] array) {

    if (array == null)
      return null;

    final int[] result = new int[array.length];

    for (int i = 0; i < result.length; i++)
      result[i] = (int) array[i];

    return result;
  }

  /**
   * Convert an array of double to an array of int
   * @param array Array to convert
   * @return a new array of double
   */
  public static int[] toArrayInt(final String[] array) {

    if (array == null)
      return null;

    final int[] result = new int[array.length];

    for (int i = 0; i < result.length; i++)
      if (array[i] != null)
        result[i] = Integer.parseInt(array[i]);

    return result;
  }

  /**
   * Convert an array of int to a list of Integers
   * @param array Array to convert
   * @return a new list of integers
   */
  public static List<Integer> toList(final int[] array) {

    if (array == null)
      return null;

    final ArrayList<Integer> result = new ArrayList<Integer>(array.length);

    for (int i = 0; i < array.length; i++)
      result.add(Integer.valueOf(array[i]));

    return result;
  }

  /**
   * Convert an array of double to a list of Double
   * @param array Array to convert
   * @return a new list of Doubles
   */
  public static List<Double> toList(final double[] array) {

    if (array == null)
      return null;

    final ArrayList<Double> result = new ArrayList<Double>(array.length);

    for (int i = 0; i < array.length; i++)
      result.add(Double.valueOf(array[i]));

    return result;
  }

  /**
   * Convert an array of booleans to a list of Booleans
   * @param array Array to convert
   * @return a new list of Booleans
   */
  public static List<Boolean> toList(final boolean[] array) {

    if (array == null)
      return null;

    final ArrayList<Boolean> result = new ArrayList<Boolean>(array.length);

    for (int i = 0; i < array.length; i++)
      result.add(Boolean.valueOf(array[i]));

    return result;
  }

  /**
   * Write an inputStream to an outputStream
   * @param is Input stream
   * @param os output stream
   * @throws IOException if an error occurs while reading or writing data
   */
  public static void writeInputStream(final InputStream is,
      final OutputStream os) throws IOException {

    if (is == null || os == null)
      return;

    byte[] buf = new byte[BUFFER_SIZE];
    int i = 0;

    while ((i = is.read(buf)) != -1)
      os.write(buf, 0, i);

  }

  /**
   * Write an inputStream to a File
   * @param is Input stream
   * @param outputFile output File
   * @throws IOException if an error occurs while reading or writing data
   */
  public static void writeInputStream(final InputStream is,
      final File outputFile) throws IOException {

    if (outputFile == null)
      throw new NullPointerException("outputFile is null");

    writeInputStream(is, new FileOutputStream(outputFile));
  }

  //
  // Constructor
  //

  /**
   * Private constructor.
   */
  private NividicUtils() {
  }

}