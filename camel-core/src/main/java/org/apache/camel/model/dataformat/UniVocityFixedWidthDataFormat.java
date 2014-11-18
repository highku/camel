/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.model.dataformat;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.DataFormat;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

/**
 * Represents the uniVocity fixed-width {@link org.apache.camel.spi.DataFormat}
 */
@XmlRootElement(name = "univocity-fixed")
@XmlAccessorType(FIELD)
public class UniVocityFixedWidthDataFormat extends UniVocityAbstractDataFormat {
    @XmlAttribute
    protected Boolean skipTrailingCharsUntilNewline;
    @XmlAttribute
    protected Boolean recordEndsOnNewline;
    @XmlAttribute
    protected String padding;

    public UniVocityFixedWidthDataFormat() {
        super("univocity-fixed");
    }

    public Boolean getSkipTrailingCharsUntilNewline() {
        return skipTrailingCharsUntilNewline;
    }

    public void setSkipTrailingCharsUntilNewline(Boolean skipTrailingCharsUntilNewline) {
        this.skipTrailingCharsUntilNewline = skipTrailingCharsUntilNewline;
    }

    public Boolean getRecordEndsOnNewline() {
        return recordEndsOnNewline;
    }

    public void setRecordEndsOnNewline(Boolean recordEndsOnNewline) {
        this.recordEndsOnNewline = recordEndsOnNewline;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    @Override
    protected void configureDataFormat(DataFormat dataFormat, CamelContext camelContext) {
        super.configureDataFormat(dataFormat, camelContext);

        if (headers != null) {
            int[] lengths = new int[headers.size()];
            for (int i = 0; i < lengths.length; i++) {
                Integer length = headers.get(i).getLength();
                if (length == null) {
                    throw new IllegalArgumentException("The length of all headers must be defined.");
                }
                lengths[i] = length;
            }
            setProperty(camelContext, dataFormat, "fieldLengths", lengths);
        }
        if (skipTrailingCharsUntilNewline != null) {
            setProperty(camelContext, dataFormat, "skipTrailingCharsUntilNewline", skipTrailingCharsUntilNewline);
        }
        if (recordEndsOnNewline != null) {
            setProperty(camelContext, dataFormat, "recordEndsOnNewline", recordEndsOnNewline);
        }
        if (padding != null) {
            setProperty(camelContext, dataFormat, "padding", singleCharOf("padding", padding));
        }
    }
}