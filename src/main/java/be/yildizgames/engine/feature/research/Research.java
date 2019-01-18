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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Contains the data for a research.
 *
 * @author Grégory Van den Borre
 */
public final class Research {

    /**
     * Contains all created Research, to check name is unique and to retrieve a Research from its name.
     */
    private static final Map<ResearchId, Research> REGISTERER = new HashMap<>();

    /**
     * Bonus obtained the this research is done.
     */
    private final BonusId bonus;

    /**
     * Research price.
     */
    private final float price;

    /**
     * Research needed to be done before making this one, optional. none.
     */
    private final ResearchId prerequisite;

    private final ResearchId id;


    private Research(final ResearchId id, final float researchPrice, final BonusId bonus, final ResearchId prerequisite) {
        super();
        this.prerequisite = prerequisite;
        this.price = researchPrice;
        this.bonus = bonus;
        this.id = id;
        REGISTERER.put(id, this);
    }

    /**
     * @param id  Research unique id.
     * @param price Research price.
     * @param bonus Bonus received when this research is bought.
     * @return The created research.
     */
    //@effects Create a new Research with no prerequisite.
    public static Research createAndRegister(final ResearchId id, final float price, final BonusId bonus) {
        return new Research(id, price, bonus, null);
    }

    /**
     * @param id          Research unique id.
     * @param price         Research price.
     * @param bonus         Bonus received when this research is bought.
     * @param prerequisite  Research needed before buying this one.
     * @return The created research.
     */
    //@effects Create a new Research with a prerequisite.
    public static Research createAndRegister(final ResearchId id, final float price, final BonusId bonus, final ResearchId prerequisite) {
        return new Research(id, price, bonus, prerequisite);
    }

    /**
     * Retrieve a research with its unique name.
     *
     * @param id Research unique id.
     * @return The research matching to the given name.
     */
    public static Research get(final ResearchId id) {
        return Research.REGISTERER.get(id);
    }

    public BonusId getBonus() {
        return bonus;
    }

    public float getPrice() {
        return price;
    }

    public ResearchId getId() {
        return id;
    }

    public Optional<ResearchId> getPrerequisite() {
        return Optional.ofNullable(prerequisite);
    }
}
