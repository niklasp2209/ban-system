<template>
    <table 
        v-if="bans?.content?.length > 0"
        class="table-auto"
    >
        <thead>
            <tr>
                <th class="px-4 py-2 text-left">Spieler</th>
                <th class="px-4 py-2 text-left">Template</th>
                <th class="px-4 py-2 text-left">Bis</th>
                <th class="px-4 py-2 text-left">Typ</th>
                <th class="px-4 py-2 text-left">Aktion</th>
            </tr>
        </thead>
        <tbody>
            <tr 
                v-for="ban in bans?.content"
                :key="ban?.id"
                class="border-t"
            >
                <td class="px-4 py-2">
                    <div class="flex items-center space-x-2">
                        <NuxtImg :src="`https://mc-heads.net/avatar/${ban?.player}`" class="w-8 h-8"/>
                        <span class="truncate w-32 md:w-full">{{ ban?.player }}</span>
                    </div>
                </td>
                <td class="px-4 py-2">{{ ban?.templateId }}</td>
                <td class="px-4 py-2">{{ getExpiryDate(ban) }}</td>
                <td class="px-4 py-2">BAN</td>
                <td class="px-4 py-2" title="Mehr Informationen">
                    <NuxtLink :to="`/bans/${ban?.id}`">
                        <svg width="24px" height="24px" viewBox="0 0 12 12" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <circle cx="2" cy="6" r="1" fill="black"/>
                            <circle cx="6" cy="6" r="1" fill="black"/>
                            <circle cx="10" cy="6" r="1" fill="black"/>
                        </svg>
                    </NuxtLink>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script setup lang="ts">
import type { PaginatedResponse } from '~/models/PaginatedResponse';
import type { Ban } from '../../models/Ban';

const { data: bans } : { data: Ref<PaginatedResponse<Ban>> } = await useFetch('/api/bans', {
    query: { 
        page: 0,
        pageSize: 25
    }
});

const getExpiryDate = (ban: Ban): string => {
    const date: Date = new Date(ban?.expiresAt);
    return `${date?.getDate()}.${date?.getMonth() + 1}.${date?.getFullYear()}`;
}
</script>