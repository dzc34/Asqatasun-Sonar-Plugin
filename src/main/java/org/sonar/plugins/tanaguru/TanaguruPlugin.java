/*
 * SonarQube Tanaguru Plugin
 * Copyright (C) 2015 Tanaguru.org
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.tanaguru;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;
import org.sonar.plugins.tanaguru.api.TanaguruConstants;
import org.sonar.plugins.tanaguru.core.Tanaguru;
import org.sonar.plugins.tanaguru.rules.Rgaa3Profile;
import org.sonar.plugins.tanaguru.rules.TanaguruRulesRepository;
import org.sonar.plugins.web.core.WebCodeColorizerFormat;
import org.sonar.plugins.web.core.WebSensor;
import org.sonar.plugins.web.core.WebSourceImporter;
import org.sonar.plugins.web.duplications.WebCpdMapping;

/**
 *
 * @author jkowalczyk
 */
@Properties({
  // Global JavaScript settings
  @Property(
          key = TanaguruPlugin.FILE_SUFFIXES_KEY,
          defaultValue = TanaguruPlugin.FILE_SUFFIXES_DEFVALUE,
          name = "File suffixes",
          description = "Comma-separated list of suffixes for files to analyze.",
          global = true,
          project = true),})
public final class TanaguruPlugin extends SonarPlugin {

  private static final String CATEGORY = "Tanaguru";
  
  @Override
  public List getExtensions() {
    ImmutableList.Builder<Object> builder = ImmutableList.builder();

    // web language
    builder.add(Tanaguru.class);

//  web files importer
    builder.add(WebSourceImporter.class);

//  web rules repository
    builder.add(TanaguruRulesRepository.class);

//  profiles
    builder.add(Rgaa3Profile.class);

//  web sensor
    builder.add(WebSensor.class);
//
//  Code Colorizer
    builder.add(WebCodeColorizerFormat.class);
//  Copy/Paste detection mechanism
    builder.add(WebCpdMapping.class);
//
    builder.addAll(pluginProperties());

    return builder.build();
  }

  // Global JavaScript constants
  public static final String FALSE = "false";
  public static final String FILE_SUFFIXES_KEY = "sonar.tanaguru.file.suffixes";
  public static final String FILE_SUFFIXES_DEFVALUE = ".html,.xhtml,.php,.jsp,.jsf,.html";
  public static final String PROPERTY_PREFIX = "sonar.tanaguru";

  private static ImmutableList<PropertyDefinition> pluginProperties() {
    return ImmutableList.of(

      PropertyDefinition.builder(TanaguruConstants.FILE_EXTENSIONS_PROP_KEY)
        .name("File suffixes")
        .description("List of file suffixes that will be scanned.")
        .category(CATEGORY)
        .defaultValue(TanaguruConstants.FILE_EXTENSIONS_DEF_VALUE)
        .onQualifiers(Qualifiers.PROJECT)
        .build()
    );
  }
}