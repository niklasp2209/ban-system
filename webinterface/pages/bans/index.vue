<template>
    <div class="flex flex-col gap-4">
        <div v-if="selectedBan">
            <BanEntry 
                :id="selectedBan" 
                @unban="unban"
                @close="unselectBan"
            />
        </div>
        <table class="min-w-full table-auto bg-white rounded-lg shadow-lg overflow-hidden">
            <thead>
                <tr class="bg-gray-800 text-white">
                    <th class="px-6 py-3 text-left">Spieler</th>
                    <th class="px-6 py-3 text-left">Template</th>
                    <th class="px-6 py-3 text-left">Bis</th>
                    <th class="px-6 py-3 text-left">Typ</th>
                    <th class="px-6 py-3 text-left">Aktion</th>
                </tr>
            </thead>
            <tbody v-if="bans?.content?.length > 0">
                <tr 
                    v-for="ban in bans?.content"
                    :key="ban?.id"
                    class="border-t hover:bg-gray-100 transition-all"
                >
                    <td class="px-6 py-4">
                        <div class="flex items-center space-x-3">
                            <NuxtImg 
                                :src="`https://mc-heads.net/avatar/${ban?.player}`" 
                                class="w-10 h-10 border-2 border-gray-300" 
                                :alt="ban?.player"
                            />
                            <span class="text-sm font-medium text-gray-800 truncate w-32 md:w-full">{{ ban?.player }}</span>
                        </div>
                    </td>
                    <td class="px-6 py-4 text-sm text-gray-700">{{ ban?.templateId }}</td>
                    <td class="px-6 py-4 text-sm text-gray-700">{{ formatDate(ban?.expiresAt) }}</td>
                    <td class="px-6 py-4 text-sm text-red-600 font-semibold"> {{ type }}</td>
                    <td class="px-6 py-4 text-sm">
                        <div 
                            class="cursor-pointer p-2 hover:bg-gray-200 rounded-full"
                            @click="selectBan(ban)"
                            title="Mehr Informationen"
                        >
                            <svg 
                                width="24px" 
                                height="24px" 
                                viewBox="0 0 12 12" 
                                fill="none" 
                                xmlns="http://www.w3.org/2000/svg"
                            >
                                <circle cx="2" cy="6" r="1" fill="black" />
                                <circle cx="6" cy="6" r="1" fill="black" />
                                <circle cx="10" cy="6" r="1" fill="black"/>
                            </svg>
                        </div>
                    </td>
                </tr>
            </tbody>
            <tbody v-else>
                <tr>
                    <td class="text-xl p-4 font-semibold">Es wurden keine Banns gefunden!</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="pt-4">
        <Pagination 
            :currentPage="currentPage" 
            :totalPages="10"
            @change="goToPage"
        />
    </div>
</template>

<script setup lang="ts">
import type { PaginatedResponse } from '~/models/PaginatedResponse';
import type { Ban } from '../../models/Ban';

const type: Ref<string | undefined> = ref<string | undefined>(useRoute()?.query?.type as string | undefined);
if (!type.value) {
    type.value = 'BAN';
}

const PAGE_SIZE: number = 25;
const currentPage: Ref<number> = ref(0);

const bans: Ref<PaginatedResponse<Ban> | undefined> = ref<PaginatedResponse<Ban> | undefined>(undefined);
const selectedBan: Ref<number | undefined> = ref<number | undefined>(undefined);

const fetchBans = async (): Promise<void> => {
    const { data } : { data: Ref<PaginatedResponse<Ban>> } = await useFetch('/api/bans', {
        query: { 
            page: currentPage.value,
            pageSize: PAGE_SIZE,
            type: type.value
        }
    });
    bans.value = data.value;
}
await fetchBans();

const goToPage = (page: number): void => {
    currentPage.value = page;
}

watch(
    () => currentPage.value,
    async () => await fetchBans()
);

const selectBan = (ban: Ban): void => {
    if (selectedBan.value === ban?.id) {
        selectedBan.value = undefined;
    }

    selectedBan.value = ban?.id;
}

const unselectBan = (): void => {
    selectedBan.value = undefined;
}

const unban = (id: number): void => {
    if (bans.value) {
        bans.value.content = bans.value?.content?.filter(ban => ban?.id !== id);
    }
}
</script>