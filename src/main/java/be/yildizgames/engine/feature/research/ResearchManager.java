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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Grégory Van den Borre
 */
public final class ResearchManager {

    private static final System.Logger LOGGER = System.getLogger(ResearchManager.class.getName());

    private static final ResearchManager INSTANCE = new ResearchManager();

    /**
     * List of all listeners.
     */
    private final List<ResearchListener> listenerList = new ArrayList<>();

    private final Map<PlayerId, Set<ResearchId>> researches = new HashMap<>();

    private ResearchManager() {
        super();
    }

    public static ResearchManager getInstance() {
        return INSTANCE;
    }

    /**
     * Add a research.
     *
     * @param res    Research to add.
     * @param player Player doing the research.
     */
    public void addResearch(final ResearchId res, final PlayerId player) {
        Set<ResearchId> list = this.researches.computeIfAbsent(player, PlayerId -> new HashSet<>());
        if (list.contains(res)) {
            this.listenerList.forEach(l -> l.researchAlreadyDone(res, player));
        } else {
            list.add(res);
            this.listenerList.forEach(l -> l.researchCompleted(res, player));
        }
    }

    /**
     * Add a listener for research event.
     *
     * @param listener Listener to add.
     */
    public void addListener(final ResearchListener listener) {
        this.listenerList.add(listener);
    }

    /**
     * Retrieve the research state for a given research.
     *
     * @param player Player to check.
     * @param id Research to check.
     * @return The state of the given research.
     */
    public ResearchState getResearchState(final PlayerId player, final ResearchId id) {
        Set<ResearchId> list = this.researches.computeIfAbsent(player, PlayerId -> new HashSet<>());
        if (list.contains(id)) {
            return ResearchState.DONE;
        }
        Research research = Research.get(id);
        if(research == null) {
            LOGGER.log(System.Logger.Level.WARNING,"Research {} not registered.", id);
            return ResearchState.UNAVAILABLE;
        }
        if (!research.getPrerequisite().isPresent()) {
            return ResearchState.AVAILABLE;
        }

        return research.getPrerequisite()
                .filter(list::contains)
                .isPresent() ? ResearchState.AVAILABLE : ResearchState.UNAVAILABLE;
    }

    /**
     * Check if a given player has a research complete.
     * @param player Player to check.
     * @param res Research to check.
     * @return <code>true</code> If the given player has completed to given research.
     */
    public boolean hasResearch(final PlayerId player, final ResearchId res) {
        return this.researches.computeIfAbsent(player, PlayerId -> new HashSet<>()).contains(res);
    }

    /**
     * Get all the researches for a player.
     * @param player Player to get the researches.
     * @return All the researches completed for the given player.
     */
    public Set<ResearchId> getResearchList(final PlayerId player) {
        return this.researches.getOrDefault(player, Collections.emptySet());
    }

    /**
     * State of a research for a player.
     *
     * @author Van den Borre Grégory
     */
    public enum ResearchState {

        /**
         * Research not already done with all prerequisites filled.
         */
        AVAILABLE,

        /**
         * Research not already done with missing prerequisite.
         */
        UNAVAILABLE,

        /**
         * Research already done.
         */
        DONE
    }

}
