import { create } from 'zustand';
import { persist } from 'zustand/middleware'

export const useAuthStore = create(
  persist(
    (set, get) => ({
      accessToken: null,
      nickname: "",
      userId: null,
      socket: null,
      setAccessToken: (token) => {
        set((prev) => ({accessToken: token}))
      },
      setNickname: (nickname) => {
        set((prev) => ({nickname: nickname}))
      },
      setUserId: (id) => {
        set((prev) => ({userId: id}))
      },
      setSocket: (socket) => {
        set((prev) => ({socket: socket}))
      },
      removeUserStatus: () => {
        set((prev) => ({
          accessToken: null,
          nickname: "",
          userId: null,
          profileImage: null,
          socket: null,
        }))
      }
    }),
    {
      name: 'auth-store',
    },
));