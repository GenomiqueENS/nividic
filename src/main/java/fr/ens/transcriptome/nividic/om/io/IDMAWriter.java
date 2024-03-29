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

package fr.ens.transcriptome.nividic.om.io;

import java.io.File;
import java.io.OutputStream;

import fr.ens.transcriptome.nividic.om.BioAssay;

/**
 * This class implement a BioAssayWriter for ID-M-A(Goulphar) streams
 * @author Lory Montout
 */
public class IDMAWriter extends BioAssayTabularWriter {

  /** Order of the fields. */
  public static final String[] FIELDS_ORDER =
      {"ID", "Name", "R", "Rb", "G", "Gb", "Mnorm", "A"};

  /** Fields names usualy readed in a ID-M-A file. */
  private static final String[] DEFAULT_FIELDS_TO_WRITE =
      {BioAssay.FIELD_NAME_ID, BioAssay.FIELD_NAME_M, BioAssay.FIELD_NAME_A,
          "R", "G"};

  //
  // Implememented methods
  //

  /**
   * Get the convert of fieldnames
   * @return The converter of fieldnames
   */
  protected FieldNameConverter getFieldNameConverter() {
    return new IDMAConverterFieldNames();
  }

  /**
   * Get an array countaining the names of the fields
   * @return an array of string countaining the names of the fields
   */
  protected String[] getFieldNamesOrder() {
    return FIELDS_ORDER;
  }

  /**
   * Get the names of the fields to read by default.
   * @the names of the fields to read by defaults
   */
  protected String[] getDefaultsFieldsToWrite() {

    return DEFAULT_FIELDS_TO_WRITE;
  }

  //
  // Constructors
  //

  /**
   * Public constructor.
   * @param filename file to read
   * @throws NividicIOException if an error occurs while reading the file or if
   *           the file is null.
   */
  public IDMAWriter(final String filename) throws NividicIOException {
    this(new File(filename));

  }

  /**
   * Public constructor.
   * @param file file to read
   * @throws NividicIOException if an error occurs while reading the file or if
   *           the file is null.
   */
  public IDMAWriter(final File file) throws NividicIOException {
    super(file);

  }

  /**
   * Public constructor
   * @param is Input stream to read
   * @throws NividicIOException if the stream is null
   */
  public IDMAWriter(final OutputStream is) throws NividicIOException {
    super(is);
  }

}