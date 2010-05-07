package org.onebusaway.gtfs.csv.schema;

import org.onebusaway.gtfs.csv.CsvEntityContext;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import java.util.Map;

public class DefaultFieldMapping extends AbstractFieldMapping {

  protected Class<?> _objFieldType;

  private Converter _converter;

  public DefaultFieldMapping(String csvFieldName, String objFieldName, Class<?> objFieldType, boolean required) {
    super(csvFieldName, objFieldName, required);
    _objFieldType = objFieldType;
    _converter = ConvertUtils.lookup(objFieldType);
    if (_converter == null)
      throw new IllegalStateException("no default converter found: csvField=" + _csvFieldName + " objField="
          + _objFieldName + " objType=" + _objFieldType);
  }

  public void translateFromCSVToObject(CsvEntityContext context, Map<String, Object> csvValues, BeanWrapper object) {

    if (isMissingAndOptional(csvValues))
      return;

    Object csvValue = csvValues.get(_csvFieldName);
    Object objValue = _converter.convert(_objFieldType, csvValue);
    object.setPropertyValue(_objFieldName, objValue);
  }

  public void translateFromObjectToCSV(CsvEntityContext context, BeanWrapper object, Map<String, Object> csvValues) {
    
    if( isMissingAndOptional(object))
      return;

    Object objValue = object.getPropertyValue(_objFieldName);
    csvValues.put(_csvFieldName, objValue);
  }

}
