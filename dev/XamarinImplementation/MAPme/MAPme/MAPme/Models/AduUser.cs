using DAL.SQLite;

namespace MAPme.Models
{
    public class AduUser
    {
        [PrimaryKey, AutoIncrement]
        public int Id { get; set; }
        [NotNull, MaxLength(50)]
        private string Username { get; set; }
        [NotNull, MaxLength(255)]
        private string Email { get; set; }
        [NotNull, MaxLength(50)]
        public string Adu { get; set; }
    }
}
