/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
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

package be.yildizgames.engine.feature.research;

import be.yildizgames.common.model.PlayerId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grégory Van den Borre
 */
public class ResearchManagerTest {

    @Nested
    public class GetResearchState {

        @Test
        public void done() {
            ResearchManager.getInstance().addResearch(ResearchId.valueOf(1), PlayerId.valueOf(1));
            assertEquals(ResearchManager.ResearchState.DONE, ResearchManager.getInstance().getResearchState(PlayerId.valueOf(1), ResearchId.valueOf(1)));
        }

        @Test
        public void noPrerequisite() {
            Research.createAndRegister(ResearchId.valueOf(2),10, BonusId.valueOf(1));
            assertEquals(ResearchManager.ResearchState.AVAILABLE, ResearchManager.getInstance().getResearchState(PlayerId.valueOf(2), ResearchId.valueOf(2)));
        }

        @Test
        void noPrerequisiteUnregistered() {
            assertEquals(ResearchManager.ResearchState.UNAVAILABLE, ResearchManager.getInstance().getResearchState(PlayerId.valueOf(3), ResearchId.valueOf(3)));
        }

        @Test
        public void prerequisiteDone() {
            Research.createAndRegister(ResearchId.valueOf(4),10, BonusId.valueOf(1));
            Research.createAndRegister(ResearchId.valueOf(5),10, BonusId.valueOf(1), ResearchId.valueOf(4));
            ResearchManager.getInstance().addResearch(ResearchId.valueOf(4), PlayerId.valueOf(5));
            assertEquals(ResearchManager.ResearchState.AVAILABLE, ResearchManager.getInstance().getResearchState(PlayerId.valueOf(5), ResearchId.valueOf(5)));
        }

        @Test
        public void prerequisiteNotDone() {
            Research.createAndRegister(ResearchId.valueOf(6),10, BonusId.valueOf(1));
            Research.createAndRegister(ResearchId.valueOf(7),10, BonusId.valueOf(1), ResearchId.valueOf(6));
            assertEquals(ResearchManager.ResearchState.UNAVAILABLE, ResearchManager.getInstance().getResearchState(PlayerId.valueOf(6), ResearchId.valueOf(7)));
        }

    }

}