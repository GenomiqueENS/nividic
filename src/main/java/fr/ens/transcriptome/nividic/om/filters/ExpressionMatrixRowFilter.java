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

package fr.ens.transcriptome.nividic.om.filters;

import java.util.ArrayList;
import java.util.List;

import fr.ens.transcriptome.nividic.om.ExpressionMatrix;
import fr.ens.transcriptome.nividic.om.ExpressionMatrixDimension;
import fr.ens.transcriptome.nividic.om.ExpressionMatrixRuntimeException;
import fr.ens.transcriptome.nividic.util.StringUtils;

/**
 * This class implements a generic filter for filtering using the value of a
 * double row of the matrix
 * @author Lory Montout
 * @author Laurent Jourdren
 */
public abstract class ExpressionMatrixRowFilter implements
    ExpressionMatrixFilter {

  /**
   * Filter a ExpressionMatrixDimension object.
   * @param em ExpressionMatrixDimension to filter
   * @return A new filtered ExpressionMatrixDimension object
   * @throws ExpressionMatrixRuntimeException if an error occurs while filtering
   *           data
   */
  public ExpressionMatrix filter(final ExpressionMatrix em)
      throws ExpressionMatrixRuntimeException {

    if (em == null)
      return null;

    ExpressionMatrixDimension d = em.getDimension(getDimensionToFilter());

    String[] rowNames = d.getRowNames();

    final int size = d.getRowCount();
    List<String> al = new ArrayList<String>();
    String[] positiveRows = new String[al.size()];

    for (int i = 0; i < size; i++)
      if (testRow(d.getRowToArray(rowNames[i])))
        al.add(new String(rowNames[i]));

    positiveRows = al.toArray(positiveRows);

    if (removePositiveRows())
      return em.subMatrixRowsExclude(positiveRows);

    return em.subMatrixRows(positiveRows);
  }

  /**
   * Count the number of the row that pass the filter
   * @param em The matrix to filter
   * @return the number of rows that pass the filter
   */
  public int count(final ExpressionMatrix em) {

    if (em == null)
      return -1;

    ExpressionMatrixDimension d = em.getDimension(getDimensionToFilter());

    String[] rowIds = d.getRowNames();

    final int size = d.getRowCount();
    int count = 0;

    for (int i = 0; i < size; i++)
      if (testRow(d.getRowToArray(rowIds[i])))
        count++;

    return count;
  }

  /**
   * Get the dimension to filter.
   * @return The dimension to filter
   */
  public abstract String getDimensionToFilter();

  /**
   * Test if filtered identifiers must be removed.
   * @return true if filtered row must be removed
   */
  public boolean removePositiveRows() {
    
    return true;
  }

  /**
   * Test the values of a Row.
   * @param values Values of M to test
   * @return true if the values must be selected
   */
  public abstract boolean testRow(final double[] values);

}