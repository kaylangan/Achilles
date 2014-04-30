/*
 * Copyright (C) 2012-2014 DuyHai DOAN
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package info.archinnov.achilles.type;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import com.datastax.driver.core.querybuilder.Clause;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import info.archinnov.achilles.listener.CASResultListener;

public class Options {

    ConsistencyLevel consistency;

    Integer ttl;

    Long timestamp;

    boolean ifNotExists;

    List<CasCondition> casConditions;

    Optional<CASResultListener> casResultListenerO = Optional.absent();

    Options() {
    }

    public Optional<ConsistencyLevel> getConsistencyLevel() {
        return Optional.fromNullable(consistency);
    }

    public Optional<Integer> getTtl() {
        return Optional.fromNullable(ttl);
    }

    public Optional<Long> getTimestamp() {
        return Optional.fromNullable(timestamp);
    }

    public boolean isIfNotExists() {
        return ifNotExists;
    }

    public List<CasCondition> getCasConditions() {
        return casConditions;
    }

    public boolean hasCasConditions() {
        return CollectionUtils.isNotEmpty(casConditions);
    }

    public Optional<CASResultListener> getCasResultListener() {
        return casResultListenerO;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(Options.class)
                .add("Consistency Level", this.consistency)
                .add("Time to live", this.ttl)
                .add("Timestamp", this.timestamp)
                .toString();
    }

    public Options duplicateWithoutTtlAndTimestamp() {
        return OptionsBuilder.withConsistency(consistency)
                .ifNotExists(ifNotExists).ifConditions(casConditions)
                .casResultListener(casResultListenerO.orNull());
    }

    public Options duplicateWithNewConsistencyLevel(ConsistencyLevel consistencyLevel) {
        return OptionsBuilder.withConsistency(consistencyLevel)
                .withTtl(ttl).withTimestamp(timestamp)
                .ifNotExists(ifNotExists).ifConditions(casConditions)
                .casResultListener(casResultListenerO.orNull());
    }

    public Options duplicateWithNewTimestamp(Long timestamp) {
        return OptionsBuilder.withConsistency(consistency)
                .withTtl(ttl).withTimestamp(timestamp)
                .ifNotExists(ifNotExists).ifConditions(casConditions)
                .casResultListener(casResultListenerO.orNull());
    }


    public static class CasCondition {

        private String columnName;
        private Object value;

        public CasCondition(String columnName, Object value) {
            this.columnName = columnName;
            this.value = value;
        }

        public void encodeValue(Object encodedValue) {
            this.value = encodedValue;
        }

        public Object getValue() {
            return this.value;
        }

        public Clause toClause() {
            return eq(columnName, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            CasCondition that = (CasCondition) o;

            return columnName.equals(that.columnName) && value.equals(that.value);

        }

        @Override
        public int hashCode() {
            int result = columnName.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

}
