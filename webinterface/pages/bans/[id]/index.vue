<template>
    Ban: {{ ban }}
</template>

<script setup lang="ts">
import type { Ban } from '~/models/Ban';

const id: number = Number(useRoute()?.params?.id) ?? -1;
const { data: ban } : { data: Ref<Ban> } = await useFetch(`/api/bans/${id}`);
</script>