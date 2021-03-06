/*
 * Copyright (C) 2012-2018 DuyHai DOAN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.archinnov.achilles.internals.config;

import java.util.Map;

import info.archinnov.achilles.annotations.FunctionRegistry;

@FunctionRegistry
public interface FunctionRegistry3_8 {

    Long textToLong(String longValue);

    Map<Integer, String> accumulate(Map<Integer, String> map, Integer clustering, String stringVal,
                                    Double doubleVal, Double lowThreshold, Double highThreshold);


    Map<Integer, String> findByDoubleValue(Integer clustering, String stringVal,
                                           Double doubleVal, Double lowThreshold, Double highThreshold);
}
