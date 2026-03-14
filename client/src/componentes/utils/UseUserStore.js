import { create } from 'zustand'

export const useUserStore = create((set) => ({
    usuario: null,
    amigos: [],
    rooms: [],
    roomSelecionadaId: null,
    usersPictures: {},
    messagesByRoom: {},

    setUsuario: (usuario) => set({ usuario }),
    setAmigos: (amigos) => set({ amigos }),
    setRooms: (rooms) => set({ rooms }),
    setRoomSelecionadaId: (roomSelecionadaId) => set({ roomSelecionadaId }),
    setUsersPictures: (usersPictures) => set({ usersPictures }),
    setMessagesByRoom: (roomId, messages) =>
    set((state) => ({
        messagesByRoom: {
            ...state.messagesByRoom,
            [roomId]: messages
        }
    })),
    addMessageToRoom: (roomId, message) =>
        set((state) => ({
            messagesByRoom: {
                ...state.messagesByRoom,
                [roomId]: [
                    ...(state.messagesByRoom[roomId] || []),
                    message
                ]
            }
        }))
}))