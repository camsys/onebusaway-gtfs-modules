package org.onebusaway.gtfs.serialization.mappings;

import org.onebusaway.csv_entities.schema.DecimalFieldMappingFactory;

import java.util.Locale;

public class CurrencyMappingFactory extends DecimalFieldMappingFactory {
    public CurrencyMappingFactory() {
        /**
         * We override the default locale to en_US so that we always use "." as the
         * decimal separator, even in locales that default to using "," insteaad.
         */
        super("#######################################.00", null);
    }
}
