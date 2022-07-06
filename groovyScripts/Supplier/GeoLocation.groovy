import org.apache.ofbiz.base.util.UtilProperties
import org.apache.ofbiz.entity.GenericValue
import org.apache.ofbiz.entity.util.EntityQuery

List<GenericValue> countries = EntityQuery.use(delegator).select("geoId", "geoName")
        .from("Geo").where("geoTypeId", "COUNTRY").queryList();
context.countries = countries;

defaultCountryGeoId = "USA"

context.defaultCountryGeoId = defaultCountryGeoId;

if (parameters.countryGeoId) {
    List<GenericValue> states = EntityQuery.use(delegator).select("geoId", "geoName")
            .from("GeoAssocAndGeoTo").where("geoIdFrom", parameters.countryGeoId).queryList();
    context.states = states;
} else {
    List<GenericValue> states = EntityQuery.use(delegator).select("geoId", "geoName")
            .from("GeoAssocAndGeoTo").where("geoIdFrom", defaultCountryGeoId).queryList();
    context.states = states;

}