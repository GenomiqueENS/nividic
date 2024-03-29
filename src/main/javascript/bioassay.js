/*
 * This file contains methods to manipulate easily BioAssay Objects.
 *
 * @author Laurent Jourdren
 *
 * TODO add function to read/write imagene Files
 */

/*
 * Creating methods
 */

/*
 * Create an empty BioAssay object.
 * @param name The name of the bioAssay (optional)
 * @return a new bioAssay object
 */
function createBioAssay(name) {

  var nividicNames = JavaImporter();
  nividicNames.importPackage(Packages.fr.ens.transcriptome.nividic.om);

  with (nividicNames)  {
  
    var ba = BioAssayFactory.createBioAssay();
    
    if (name!=null) { ba.setName(name); }
    return ba;
  }

}

/*
 * Reader methods
 */

/*
 * Internal function.
 * Get the bioassay reader object
 * @param file File to read
 * @param type Type of the file to read
 * @return a BioAssayReader object
 */
function _getBioAssayReader(file, type) {

  var nividicNames = JavaImporter();
  nividicNames.importPackage(Packages.fr.ens.transcriptome.nividic.om.io);

  with (nividicNames)  {
    switch(type) {
  
      case "gpr": 
        return new GPRReader(file);
        
      case "gal": 
        return new GALReader(file);

      case "idma": 
        return new IDMAReader(file);

      case "total.summary":
        return new Packages.fr.ens.transcriptome.nividic.om.sgdb.io.TotalSummaryReader(file);
        
      case "universal":
        return new UniversalBioAssayReader(file);
    }
  }

  return null;
}

/*
 * Read a bioAssay.
 * @param file File(s) to read
 * @param type Type of the file (gpr,idma...)
 * @param allFields Read all the fields or only default fields
 * @return a BioAssay file
 */
function readBioAssay(file, type, allFields, comma) {

  if (file==null) { return null; }
  
  if (file.constructor==String) { file = sf(file); }  

  if (file instanceof Array) {

    var result = new Array;
    for (i=0; i<file.length;i++) {
      
      var reader = _getBioAssayReader(file[i], type);

      if (allFields==true) { 
        reader.addAllFieldsToRead(); 
      }

      if (comma==true) {
        reader.setCommaDecimalSeparator(true);
      }

      result[i]=reader.read();
      result[i].setName(file[i].getName());
    }

    return result;
  } 
  else {

      var reader = _getBioAssayReader(file, type);

      if (allFields==true) { 
        reader.addAllFieldsToRead(); 
      }

      if (comma==true) {
        reader.setCommaDecimalSeparator(true);
      }

      var ba = reader.read();
      ba.setName(file.getName());
      return ba;
  }
}

/**
 * Shortcut to read GPR file(s).
 * @param file File(s) to read
 * @param allField read all fields
 * @return A BioAssay Object
 */
function readGPR(file, allFields) {

  return readBioAssay(file, "gpr", allFields, false);
}

/**
 * Shortcut to read GAL file(s).
 * @param file File(s) to read
 * @param allField read all fields
 * @return A BioAssay Object
 */
function readGAL(file, allFields) {

  return readBioAssay(file, "gal", allFields, false);
}



/**
 * Shortcut to read IDMA file(s).
 * @param file File(s) to read
 * @param allField read all fields
 * @param comma true if comma is the decimal separator
 * @return A BioAssay Object
 */
function readIDMA(file, allFields, comma) {

  return readBioAssay(file, "idma", allFields, comma);
}

/**
 * Shortcut to read total.summary file(s).
 * @param file File(s) to read
 * @param comma true if comma is the decimal separator
 * @return A BioAssay Object
 */
function readTotalSummary(file, comma) {

  return readBioAssay(file, "total.summary", false, comma);
}

/**
 * Shortcut to read undefined type bioassay file(s).
 * @param file File(s) to read
 * @param comma true if comma is the decimal separator
 * @return A BioAssay Object
 */
function readUndefinedBioAssay(file, comma) {

  return readBioAssay(file, "universal", false, comma);
}

/*
 * Writer methods
 */

/*
 * Internal function.
 * Get the bioassay reader object
 * @param file File to read
 * @param type Type of the file to read
 * @return a BioAssayReader object
 */
function _getBioAssayWriter(file, type) {

  if (file.constructor==String) { file = sf(file); }

  var nividicNames = JavaImporter();
  nividicNames.importPackage(Packages.fr.ens.transcriptome.nividic.om.io);

  with (nividicNames)  {
    switch(type) {
  
      case "gpr": 
        return new GPRWriter(file);
        
     case "gal": 
        return new GALWriter(file);

      case "idma": 
        return new IDMAWriter(file);

      case "soft":
        return new Packages.fr.ens.transcriptome.nividic.sgdb.io.SOFTBioAssayWriter(file);
      
      case "total.summary":
        return new Packages.fr.ens.transcriptome.nividic.sgdb.io.TotalSummaryWriter(file);
       
      case  "total.summary.xsl":
        var writer = new Packages.fr.ens.transcriptome.nividic.sgdb.io.TotalSummaryWriter(file);
        writer.setXSLBackend(true);
        return writer;
    }
  }

  return null;
}

/*
 * Write a bioAssay.
 * @param bioAssay BioAssay to write
 * @param file File(s) to write
 * @param type Type of the file (gpr,idma...)
 * @param allFields Write all the fields or only default fields
 * @return nothing
 */
function writeBioAssay(bioAssay, file, type, allFields, translator) {

  var writer = _getBioAssayWriter(file, type);

  if (allFields==true) { 
    writer.addAllFieldsToWrite(); 
  }
  if (translator!=undefined)
    writer.setTranslator(translator);

  writer.write(bioAssay);
}

/**
 * Shortcut to write GPR file.
 * @param file File to write
 * @return nothing
 */
function writeGPR(bioAssay, file) {

  writeBioAssay(bioAssay, file, "gpr", true);
}

/**
 * Shortcut to write GAL file.
 * @param file File to write
 * @return nothing
 */
function writeGAL(bioAssay, file) {

  writeBioAssay(bioAssay, file, "gal", true);
}

/**
 * Shortcut to write IDMA file.
 * @param file File to write
 * @return nothing
 */
function writeIDMA(bioAssay, file) {

  writeBioAssay(bioAssay, file, "idma", true);
}

/**
 * Shortcut to write SOFT file.
 * @param file File to write
 * @return nothing
 */
function writeSOFT(bioAssay, file) {

  writeBioAssay(bioAssay, file, "soft", true);
}

/**
 * Shortcut to write total.summary file.
 * @param file File to write
 * @param translator Translator to use
 * @return nothing
 */
function writeTotalSummary(bioAssay, file, translator) {

  writeBioAssay(bioAssay, file, "total.summary", true, translator);
}

/**
 * Shortcut to write total.summary file.
 * @param file File to write
 * @param translator Translator to use
 * @return nothing
 */
function writeTotalSummaryXLS(bioAssay, file, translator) {

  writeBioAssay(bioAssay, file, "total.summary.xsl", true, translator);
}

/*
 *  Sort methods
 */

/*
 * Get a sorter of bioAssay objects.
 * @return the sorter
 */
function createMASorter() {

  return new Packages.fr.ens.transcriptome.nividic.om.filters.BioAssayMASorterComparator();
}

/*
 * Modify functions
 */
 
 /*
  * Merge all the rows with the same ids.
  * @param bioAssay bioAssay to merge
  * @return a new BioAssay object
  */ 
function mergeInnerIdsReplicates(bioAssay) {

  return new Packages.fr.ens.transcriptome.nividic.om.BioAssayUtils.mergeInnerIdsReplicates(bioAssay);
}

 /*
  * Merge all the rows with the same descriptions.
  * @param bioAssay bioAssay to merge
  * @return a new BioAssay object
  */ 
function mergeInnerDescriptionsReplicates(bioAssay) {

  return new Packages.fr.ens.transcriptome.nividic.om.BioAssayUtils.mergeInnerDescriptionsReplicates(bioAssay);
}

 /*
  * Rename the identifiers of the bioAssay with uniques Indentifers
  * @param bioAssay bioAssay to merge
  * @return a new BioAssay object
  */ 
function renameBioAssayIdsWithUniqueIdentifiers(bioAssay) {

  new Packages.fr.ens.transcriptome.nividic.om.BioAssayUtils.renameIdsWithUniqueIdentifiers(bioAssay);
}

/*
 * BioAssay FieldNames
 */
   
 /** Column name for red data. */
  var FIELD_NAME_RED = "red";
  /** Column name for green data. */
  var FIELD_NAME_GREEN = "green";
  /** Column name for flags data. */
  var FIELD_NAME_FLAG = "flags";
  /** Column name for name data. */
  var FIELD_NAME_ID = "id";
  /** Column name for ratio data. */
  var FIELD_NAME_RATIO = "ratio";
  /** Column name for bright data. */
  var FIELD_NAME_BRIGHT = "bright";
  /** Column name for description data. */
  var FIELD_NAME_DESCRIPTION = "description";
  /** Column name for a coordinate of a MA plot. */
  var FIELD_NAME_A = "a";
  /** Column name for m coordinate of a MA plot. */
  var FIELD_NAME_M = "m";
  /** Column name for the standard deviation of a values. */
  var FIELD_NAME_STD_DEV_A = "stddeva";
  /** Column name for the standard deviation of m values. */
  var FIELD_NAME_STD_DEV_M = "stddevm";

/*
 * Flags values
 */

  /** Flag bad. */
  var FLAG_BAD = -100;
  /** Flag abscent. */
  var FLAG_ABSCENT = -75;
  /** Flag not found. */
  var FLAG_NOT_FOUND = -50;
  /** Flag unflagged. */
  var FLAG_UNFLAGGED = 0;
  /** Flag normalized. */
  var FLAG_NORMALIZED = 1;
  /** Flag good. */
  var FLAG_GOOD = 100;
 

/*
 *  Filter methods
 */


/*
 * Create a filter on values greater or equals to the parameter.
 * @param field Field to use
 * @param threshold
 * @param condition
 */
function createThresholdFilter(field, condition, threshold) {

  if (field==null || threshold==null || condition ==null) return null;

  var nividicNames = JavaImporter();
  nividicNames.importPackage(Packages.fr.ens.transcriptome.nividic.om.filters);

  with(nividicNames) {

    /*obj = { test: function (v) { return !isNaN(v)  && v <= threshold; } };
    return new BioAssayMFilter(obj);*/
    
    return new BioAssayDoubleThresholdFilter(field, condition, threshold);
  }

}


/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createInfFilter(field,threshold) {

  return createThresholdFilter(field, "<=", threshold);
}

/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createSupFilter(field, threshold) {

  return createThresholdFilter(field, ">=", threshold);
}

/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createAInfFilter(threshold) {

  return createInfFilter(FIELD_NAME_A, threshold);
}

/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createASupFilter(threshold) {

  return createSupFilter(FIELD_NAME_A, threshold);
}


/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createMSupFilter(threshold) {

  return createSupFilter(FIELD_NAME_M, threshold);
}

/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createMInfFilter(threshold) {

  return createInfFilter(FIELD_NAME_M, threshold);
}

/*
 * Create a filter on values greater or equals to the parameter.
 * @param threshold
 */
function createAInfFilter(threshold) {

 return createInfFilter(FIELD_NAME_A, threshold);
}


/**
 * Swap the M values of a BioAssay.
 * @param bioAssay The bioAssay to swap
 * @return nothing
 */
function swapBioAssay(bioAssay) {

  var nividicNames = JavaImporter();
  nividicNames.importPackage(Packages.fr.ens.transcriptome.nividic.om);

  with(nividicNames) {
    
    BioAssayUtils.swap(bioAssay);
  }
}





