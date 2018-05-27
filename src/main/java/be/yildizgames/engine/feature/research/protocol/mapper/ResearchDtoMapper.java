/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.engine.feature.research.protocol.mapper;

import be.yildizgames.common.mapping.MappingException;
import be.yildizgames.common.mapping.ObjectMapper;
import be.yildizgames.common.mapping.Separator;
import be.yildizgames.engine.feature.research.protocol.ResearchDto;

/**
 * @author Grégory Van den Borre
 */
public class ResearchDtoMapper implements ObjectMapper<ResearchDto> {

    private static final ResearchDtoMapper INSTANCE = new ResearchDtoMapper();

    private ResearchDtoMapper() {
        super();
    }

    public static ResearchDtoMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public ResearchDto from(String s) throws MappingException {
        assert s != null;
        String[] v = s.split(Separator.OBJECTS_SEPARATOR);
        try {
            return new ResearchDto(ResearchIdMapper.getInstance().from(v[0]));
        } catch (IndexOutOfBoundsException e) {
            throw new MappingException(e);
        }
    }

    @Override
    public String to(ResearchDto dto) {
        assert dto != null;
        return ResearchIdMapper.getInstance().to(dto.research);
    }
}
