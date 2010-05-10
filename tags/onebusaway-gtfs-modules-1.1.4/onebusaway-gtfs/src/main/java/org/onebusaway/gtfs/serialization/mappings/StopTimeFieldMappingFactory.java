package org.onebusaway.gtfs.serialization.mappings;

import org.onebusaway.gtfs.csv.CsvEntityContext;
import org.onebusaway.gtfs.csv.schema.AbstractFieldMapping;
import org.onebusaway.gtfs.csv.schema.BeanWrapper;
import org.onebusaway.gtfs.csv.schema.EntitySchemaFactory;
import org.onebusaway.gtfs.csv.schema.FieldMapping;
import org.onebusaway.gtfs.csv.schema.FieldMappingFactory;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StopTimeFieldMappingFactory implements FieldMappingFactory {

  private static DecimalFormat _format = new DecimalFormat("00");

  private static Pattern _pattern = Pattern.compile("^(\\d{1,2}):(\\d{2}):(\\d{2})$");

  public FieldMapping createFieldMapping(EntitySchemaFactory schemaFactory, String csvFieldName, String objFieldName,
      Class<?> objFieldType, boolean required) {
    return new StopTimeFieldMapping(csvFieldName, objFieldName, required);
  }

  private static class StopTimeFieldMapping extends AbstractFieldMapping {

    public StopTimeFieldMapping(String csvFieldName, String objFieldName, boolean required) {
      super(csvFieldName, objFieldName, required);
    }

    public void translateFromCSVToObject(CsvEntityContext context, Map<String, Object> csvValues, BeanWrapper object) {

      if (isMissingAndOptional(csvValues))
        return;

      Object value = csvValues.get(_csvFieldName);
      Matcher m = _pattern.matcher(value.toString());
      if (!m.matches())
        throw new IllegalArgumentException("invalid stop_time: " + value);

      int hours = Integer.parseInt(m.group(1));
      int minutes = Integer.parseInt(m.group(2));
      int seconds = Integer.parseInt(m.group(3));

      object.setPropertyValue(_objFieldName, seconds + 60 * (minutes + 60 * hours));
    }

    public void translateFromObjectToCSV(CsvEntityContext context, BeanWrapper object, Map<String, Object> csvValues) {

      int t = (Integer) object.getPropertyValue(_objFieldName);

      if (t < 0) {
        csvValues.put(_csvFieldName, "");
        return;
      }

      int hours = t / (60 * 60);
      t = t - hours * (60 * 60);
      int minutes = t / 60;
      t = t - minutes * 60;
      int seconds = t;

      String value = _format.format(hours) + ":" + _format.format(minutes) + ":" + _format.format(seconds);
      csvValues.put(_csvFieldName, value);
    }
  }

}
