/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2024 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.viaversion.viaversion.rewriter.entitydata;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.TrackedEntity;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityDataHandlerEventImpl implements EntityDataHandlerEvent {
    private final UserConnection connection;
    private final TrackedEntity trackedEntity;
    private final int entityId;
    private final List<EntityData> metadataList;
    private final EntityData meta;
    private List<EntityData> extraData;
    private boolean cancel;

    public EntityDataHandlerEventImpl(UserConnection connection, @Nullable TrackedEntity trackedEntity, int entityId, EntityData meta, List<EntityData> metadataList) {
        this.connection = connection;
        this.trackedEntity = trackedEntity;
        this.entityId = entityId;
        this.meta = meta;
        this.metadataList = metadataList;
    }

    @Override
    public UserConnection user() {
        return connection;
    }

    @Override
    public int entityId() {
        return entityId;
    }

    @Override
    public @Nullable TrackedEntity trackedEntity() {
        return trackedEntity;
    }

    @Override
    public EntityData data() {
        return meta;
    }

    @Override
    public void cancel() {
        this.cancel = true;
    }

    @Override
    public boolean cancelled() {
        return cancel;
    }

    @Override
    public @Nullable EntityData dataAtIndex(int index) {
        for (EntityData meta : metadataList) {
            if (index == meta.id()) {
                return meta;
            }
        }
        return null;
    }

    @Override
    public List<EntityData> dataList() {
        return Collections.unmodifiableList(metadataList);
    }

    @Override
    public @Nullable List<EntityData> extraData() {
        return extraData;
    }

    @Override
    public void createExtraData(EntityData metadata) {
        if (extraData == null) {
            extraData = new ArrayList<>();
        }

        extraData.add(metadata);
    }
}
