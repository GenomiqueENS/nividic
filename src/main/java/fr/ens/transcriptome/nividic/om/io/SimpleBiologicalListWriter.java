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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import fr.ens.transcriptome.nividic.NividicRuntimeException;
import fr.ens.transcriptome.nividic.om.BiologicalList;

public class SimpleBiologicalListWriter implements BiologicalListWriter {

  private OutputStream os;

  /**
   * Write the Biological List.
   * @param list Biological List to write
   */
  public void write(final BiologicalList list) {

    if (this.os == null)
      throw new NividicRuntimeException("The stream to read is null");

    final BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
        this.os));

    Iterator it = list.iterator();

    try {

      while (it.hasNext())
        out.write(it.next() + "\n");

      out.close();

    } catch (IOException e) {
      throw new NividicRuntimeException("Error while writing the stream");
    }

  }

  //
  // Constructors
  //

  /**
   * Public constructor.
   * @param os InputStream to write
   */
  public SimpleBiologicalListWriter(final OutputStream os) {
    this.os = os;
  }

  /**
   * Public constructor.
   * @param file File to write
   * @throws FileNotFoundException if an error occurs while creating the stream
   */
  public SimpleBiologicalListWriter(final File file)
      throws FileNotFoundException {

    this(new FileOutputStream(file));
  }

  /**
   * Public constructor.
   * @param filename file to write
   * @throws FileNotFoundException if an error occurs while creating the stream
   */
  public SimpleBiologicalListWriter(final String filename)
      throws FileNotFoundException {

    this(new File(filename));
  }

}