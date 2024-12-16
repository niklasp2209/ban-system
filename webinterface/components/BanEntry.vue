<template>
    <div class="border shadow-md rounded-lg bg-white p-4 max-w-md mx-auto relative">
        <div class="h-1 bg-white pb-4">
            <button 
                class="absolute top-2 right-2 z-10 text-gray-600 hover:text-gray-800 focus:outline-none"
                @click="close"
            >
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" class="w-5 h-5">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
            </button>
        </div>
        <div 
            v-if="status === 'idle' || unbanStatus === 'idle'"
            class="flex justify-center"
        >
            <AnimationLoadingSpinner />
        </div>
        <div 
            v-else-if="status === 'error'" 
            class="flex flex-col items-center p-6 bg-red-50 border border-red-200 rounded-lg shadow-md max-w-md mx-auto"
        >
            <div class="flex items-center gap-4 mb-4">
                <h2 class="text-xl font-semibold text-red-700">Etwas ist schiefgelaufen!</h2>
            </div>
            <p class="text-gray-700 text-center mb-4">
                Versuche es erneut oder kontaktiere einen Administrator.
            </p>
            <button 
                class="px-4 py-2 bg-red-600 text-white rounded-xl hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
                @click="refresh"
            >
                Erneut versuchen
            </button>
        </div>
        <div 
            v-else-if="unbanStatus === 'success'"
            class="flex flex-col items-center p-6 bg-green-50 border border-green-200 rounded-lg shadow-md max-w-md mx-auto"
        >
            <div class="flex items-center gap-4 mb-4">
                <h2 class="text-xl font-semibold text-green-700">Strafe wurde aufgehoben</h2>
            </div>
            <p class="text-green-600 text-center mb-4">
                Die Strafe des Spielers {{ ban?.player }} wurde aufgehoben.
            </p>
        </div>
        <div v-else-if="status === 'success' && ban">
            <div class="flex items-center gap-4 border-b pb-4 mb-4">
                <NuxtImg 
                    :src="`https://mc-heads.net/avatar/${ban?.player}`" 
                    class="w-16 h-16 border border-gray-300" 
                    :alt="ban?.player" 
                />
                <div>
                    <span class="text-lg font-semibold text-gray-700">{{ ban?.player }}</span>
                </div>
            </div>
            <div class="flex flex-col gap-2">
                <div class="flex justify-between">
                    <span class="font-medium text-gray-600">Template-ID:</span>
                    <span class="text-gray-800">{{ ban?.templateId }}</span>
                </div>
                <div class="flex justify-between">
                    <span class="font-medium text-gray-600">Grund:</span>
                    <span class="text-gray-800">{{ ban?.reason }}</span>
                </div>
                <div class="flex justify-between">
                    <span class="font-medium text-gray-600">Gebannt von:</span>
                    <span class="text-gray-800">{{ ban?.bannedBy }}</span>
                </div>
                <div class="flex justify-between">
                    <span class="font-medium text-gray-600">Von:</span>
                    <span class="text-gray-800">{{ formatDate(ban?.bannedAt) }}</span>
                </div>
                <div class="flex justify-between">
                    <span class="font-medium text-gray-600">Bis:</span>
                    <span class="text-gray-800">{{ formatDate(ban?.expiresAt) }}</span>
                </div>
            </div>

            <div class="flex justify-center pt-6">
                <button 
                    class="px-4 py-2 bg-red-600 text-white rounded-xl hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-500"
                    @click="unban"
                >
                    Strafe aufheben
                </button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import type { BanListEntry } from '~/models/BanListEntry';

const props = defineProps({
    id: {
        type: Number,
        required: true,
    },
});
const emit = defineEmits(['close', 'unban']);

const { status, data: ban, refresh }: { status: Ref<string>, data: Ref<BanListEntry>, refresh: any } = await useFetch(`/api/bans/${props.id}`);
const unbanStatus: Ref<string | undefined> = ref<string | undefined>(undefined);

const unban = async (): Promise<void> => {
    unbanStatus.value = 'idle';
    const { status } = await useFetch<void>('/api/unban', {
        method: 'POST',
        body: { id: props?.id }
    });

    unbanStatus.value = status.value;
}

const close = (): void => {
    emit('close');
    if (unbanStatus.value === 'success') {
        emit('unban', props.id);
    }
}
</script>