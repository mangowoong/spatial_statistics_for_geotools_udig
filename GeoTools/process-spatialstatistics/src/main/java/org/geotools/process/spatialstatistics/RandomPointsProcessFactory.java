/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 *
 *    (C) 2014, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.process.spatialstatistics;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.geotools.data.Parameter;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.NameImpl;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.process.Process;
import org.geotools.util.KVP;
import org.geotools.util.logging.Logging;
import org.opengis.util.InternationalString;

/**
 * RandomPointsProcessFactory
 * 
 * @author Minpa Lee, MangoSystem
 * 
 * @source $URL$
 */
public class RandomPointsProcessFactory extends SpatialStatisticsProcessFactory {
    protected static final Logger LOGGER = Logging.getLogger(RandomPointsProcessFactory.class);

    private static final String PROCESS_NAME = "RandomPoints";

    // RandomPoints(Integer pointCount, ReferenceEnvelope extent): SimpleFeatureCollection
    // RandomPoints(Integer pointCount, SimpleFeatureCollection polygonFeatures): SimpleFeatureCollection

    public RandomPointsProcessFactory() {
        super(new NameImpl(NAMESPACE, PROCESS_NAME));
    }

    @Override
    protected Process create() {
        return new RandomPointsProcess(this);
    }

    @Override
    public InternationalString getTitle() {
        return getResource("RandomPoints.title");
    }

    @Override
    protected InternationalString getDescription() {
        return getResource("RandomPoints.description");
    }

    /** pointCount */
    protected static final Parameter<Integer> pointCount = new Parameter<Integer>("pointCount",
            Integer.class, getResource("RandomPoints.pointCount.title"),
            getResource("RandomPoints.pointCount.description"), true, 1, 1, 1000, null);

    /** extent */
    protected static final Parameter<ReferencedEnvelope> extent = new Parameter<ReferencedEnvelope>(
            "extent", ReferencedEnvelope.class, getResource("RandomPoints.extent.title"),
            getResource("RandomPoints.extent.description"), false, 0, 1, null, null);

    /** polygonFeatures */
    protected static final Parameter<SimpleFeatureCollection> polygonFeatures = new Parameter<SimpleFeatureCollection>(
            "polygonFeatures", SimpleFeatureCollection.class,
            getResource("RandomPoints.polygonFeatures.title"),
            getResource("RandomPoints.polygonFeatures.description"),
            false, 0, 1, null, new KVP(Parameter.FEATURE_TYPE, "Polygon"));

    @Override
    protected Map<String, Parameter<?>> getParameterInfo() {
        HashMap<String, Parameter<?>> parameterInfo = new LinkedHashMap<String, Parameter<?>>();
        parameterInfo.put(pointCount.key, pointCount);
        parameterInfo.put(extent.key, extent);
        parameterInfo.put(polygonFeatures.key, polygonFeatures);
        return parameterInfo;
    }

    /** result */
    protected static final Parameter<SimpleFeatureCollection> RESULT = new Parameter<SimpleFeatureCollection>(
            "result", SimpleFeatureCollection.class, getResource("RandomPoints.result.title"),
            getResource("RandomPoints.result.description"));

    static final Map<String, Parameter<?>> resultInfo = new TreeMap<String, Parameter<?>>();
    static {
        resultInfo.put(RESULT.key, RESULT);
    }

    @Override
    protected Map<String, Parameter<?>> getResultInfo(Map<String, Object> parameters)
            throws IllegalArgumentException {
        return Collections.unmodifiableMap(resultInfo);
    }

}
