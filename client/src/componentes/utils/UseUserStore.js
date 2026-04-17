import { create } from 'zustand'

export const useUserStore = create((set) => ({
    usuario: null,
    amigos: [],
    receivedSolicitations: [],
    rooms: [],
    roomSelecionadaId: null,
    usersPictures: {},
    messagesByRoom: {},
    stompClient: null,

    // ================= USER =================
    setUsuario: (usuario) => set({ usuario }),

    // ================= AMIGOS =================
    setAmigos: (amigos) => set({ amigos }),

    addNewFriend: (amigo) => 
        set((state) => ({
            amigos: state.amigos.some(a => a.id === amigo.id)
                ? state.amigos
                : [...state.amigos, amigo]
        })),

    removeFriend: (id) =>
        set((state) => ({
            amigos: state.amigos.filter(a => a.id !== id)
        })),

    // ================= SOLICITAÇÕES =================
    setReceivedSolicitations: (receivedSolicitations) => set({ receivedSolicitations }),

    addReceivedSolicitation: (amizade) => 
        set((state) => ({
            receivedSolicitations: state.receivedSolicitations.some(s => s.id === amizade.id)
                ? state.receivedSolicitations
                : [...state.receivedSolicitations, amizade]
        })),

    removeReceivedSolicitation: (id) =>
        set((state) => ({
            receivedSolicitations: state.receivedSolicitations.filter(s => s.id !== id)
        })),

    // ================= ROOMS =================
    setRooms: (rooms) => set({ rooms }),

    addNewRoom: (room) => 
        set((state) => ({
            rooms: state.rooms.some(r => r.id === room.id)
                ? state.rooms
                : [...state.rooms, room]
        })),

    removeRoom: (id) =>
        set((state) => ({
            rooms: state.rooms.filter(r => r.id !== id)
        })),

    setRoomSelecionadaId: (roomSelecionadaId) => set({ roomSelecionadaId }),

    // ================= PICTURES =================
    setUsersPictures: (usersPictures) => set({ usersPictures }),

    addUserPicture: (userId, pictureUrl) =>
        set((state) => ({
            usersPictures: {
                ...state.usersPictures,
                [userId]: pictureUrl
            }
        })),

    // ================= MESSAGES =================
    setMessagesByRoom: (roomId, messages) =>
        set((state) => ({
            messagesByRoom: {
                ...state.messagesByRoom,
                [roomId]: messages
            }
        })),

    addMessageToRoom: (roomId, message) =>
        set((state) => {
            const currentMessages = state.messagesByRoom[roomId] || []
            
            // evita duplicação de mensagem (importante pra websocket/retry)
            const alreadyExists = currentMessages.some(m => m.id === message.id)

            return {
                messagesByRoom: {
                    ...state.messagesByRoom,
                    [roomId]: alreadyExists
                        ? currentMessages
                        : [...currentMessages, message]
                }
            }
        }),

    setStompClient: (client) => set({ stompClient: client }),

    // ================= ACTION COMPLETA =================
    // quando aceita amizade
    acceptFriendship: ({ amigo, room, solicitationId }) =>
        set((state) => {
            const friendExists = state.amigos.some(a => a.id === amigo.id)
            const roomExists = state.rooms.some(r => r.id === room.id)

            return {
                amigos: friendExists ? state.amigos : [...state.amigos, amigo],
                rooms: roomExists ? state.rooms : [...state.rooms, room],
                receivedSolicitations: state.receivedSolicitations.filter(
                    s => s.id !== solicitationId
                )
            }
        })
}))